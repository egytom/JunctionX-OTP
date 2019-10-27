package otp.junctionx.atm.finder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import otp.junctionx.atm.finder.dto.*;
import otp.junctionx.atm.finder.dto.helper.AtmData;
import otp.junctionx.atm.finder.dto.helper.AtmResult;
import otp.junctionx.atm.finder.dto.helper.Coord;
import otp.junctionx.atm.finder.dto.helper.google.Element;
import otp.junctionx.atm.finder.dto.helper.google.GoogleMapsData;
import otp.junctionx.atm.finder.model.Atm;
import otp.junctionx.atm.finder.repository.AtmRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AtmFinderService {

    private int relevantAtmCount = 3;
    private int averageCustomerTimeInSeconds = 45;

    private AtmRepository repository;

    public AtmFinderService(AtmRepository repository) {
        this.repository = repository;
    }

    public List<AtmResponse> getAllAtmWithAllInfo(SearchRequest searchRequest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<AtmData> atmDataList = readAtmLocationResponseFromJson();
            List<AtmResult> closestAtms;
            List<AtmResponse> response;

            if (searchRequest.isDepositRequired) {
                List<AtmData> filteredAtmDataList = atmDataList.stream().filter(a -> a.deposit).collect(Collectors.toList());
                closestAtms = getClosestAtms(searchRequest, filteredAtmDataList, mapper);
                response = getAtmResponse(searchRequest, closestAtms, filteredAtmDataList);
            } else {
                closestAtms = getClosestAtms(searchRequest, atmDataList, mapper);
                response = getAtmResponse(searchRequest, closestAtms, atmDataList);
            }

            return response;
        } catch (IOException ioe) {
            log.info("Json reading failed. Error: " + ioe.getMessage());
            return Arrays.asList(new AtmResponse());
        }
    }

    public List<AtmData> getAllAtmLocations() {
        return readAtmLocationResponseFromJson();
    }

    public void writeSelectedAtm(String id) {
        Optional<Atm> atmOptional = repository.findById(id);
        Atm atm;

        if (atmOptional.isPresent()) {
            atm = atmOptional.get();
            int customerCount = atm.getExpectedCustomers();
            atm.setExpectedCustomers(customerCount + 1);
        } else {
            atm = new Atm();
            atm.setId(id);
            atm.setExpectedCustomers(1);
        }

        repository.save(atm);
    }

    private List<AtmData> readAtmLocationResponseFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("files" + File.separator + "data.json"), new TypeReference<List<AtmData>>() {
            });
        } catch (IOException ioe) {
            log.info("Json reading failed. Error: " + ioe.getMessage());
            return Arrays.asList(new AtmData());
        }
    }

    private List<AtmResult> getClosestAtms(SearchRequest searchRequest, List<AtmData> atmDataList, ObjectMapper mapper) throws IOException {
        HashMap<String, Integer> mapSeconds = new HashMap<>();
        List<Coord> destCoords = atmDataList.stream().map(a -> a.coord).collect(Collectors.toList());
        String url = getUrl(searchRequest.coord, destCoords);
        GoogleMapsData googleMapsData = mapper.readValue(new URL(url), GoogleMapsData.class);

        int num = 0;
        for (Element e : googleMapsData.rows.get(0).elements) {
            mapSeconds.put(atmDataList.get(num++).id, e.duration.value);
        }

        List<String> lowestIDs = mapSeconds.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .limit(relevantAtmCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<AtmResult> atmResults = new ArrayList<>();
        for (String id : lowestIDs) {
            AtmResult atmResult = AtmResult.builder().id(id).travelSeconds(mapSeconds.get(id)).build();
            atmResults.add(atmResult);
        }

        return atmResults;
    }

    private String getUrl(Coord startCoord, List<Coord> destinationCoordList) {
        String destinations = "";
        for (int i = 0; i < destinationCoordList.size(); i++) {
            destinations += destinationCoordList.get(i).x + "%2C" + destinationCoordList.get(i).y;
            if (i != destinationCoordList.size()-1) {
                destinations += "|";
            }
        }

        return "https://maps.googleapis.com/maps/api/distancematrix/json?units=kilometers&origins="
                + startCoord.x + "%2C" + startCoord.y +
                "&mode=walking&key=AIzaSyDVH3Psi4cx-yp4-CVzsOYl3yMFSiR3lIA&destinations="
                + destinations;
    }

    private List<AtmResponse> getAtmResponse(SearchRequest searchRequest, List<AtmResult> closestAtms, List<AtmData> atmDataList) {
        List<AtmResponse> response = new ArrayList<>();
        Map<String, Integer> atmMap = getFutureCustomersCount();

        for (AtmResult atmResult : closestAtms) {
            String id = atmResult.id;
            AtmResponse atmResponse = AtmResponse.builder()
                    .id(id)
                    .countOfFutureCustomers(atmMap.getOrDefault(id,0))
                    .build();

            Optional<AtmData> atmData = atmDataList.stream().filter(a -> a.id.equals(id)).findFirst();

            int waitingSeconds;
            if (atmData.isPresent()) {
                AtmData data = atmData.get();
                atmResponse.address = data.address;
                atmResponse.coord = data.coord;
                atmResponse.date = data.date;
                atmResponse.day = data.day;
                atmResponse.isDepositAvailable = data.deposit;

                waitingSeconds = atmResult.travelSeconds + data.sections.get(searchRequest.section) * averageCustomerTimeInSeconds;
            } else {
                waitingSeconds = atmResult.travelSeconds;
            }
            atmResponse.queueAndTravelTime = getTimeStringFromSeconds(waitingSeconds);

            response.add(atmResponse);
        }

        return response;
    }

    private Map<String, Integer>  getFutureCustomersCount() {
        List<Atm> atmList = repository.findAll();
        return atmList.stream().collect(Collectors.toMap(Atm::getId, Atm::getExpectedCustomers));
    }

    private String getTimeStringFromSeconds(long seconds) {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;

        if (hours > 0) {
            return hours + " hours " + mins + " minutes";
        } else {
            return mins + " minutes";
        }

    }

}
