package otp.junctionx.atm.finder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otp.junctionx.atm.finder.dto.AtmResponse;
import otp.junctionx.atm.finder.dto.AtmLocationResponse;
import otp.junctionx.atm.finder.dto.SearchRequest;
import otp.junctionx.atm.finder.service.AtmFinderService;

import javax.validation.Valid;
import java.util.List;

import static otp.junctionx.atm.finder.util.MapperUtil.mapAtmDataListToAtmLocationResponseList;

@Slf4j
@RestController
public class AtmFinderController {

    AtmFinderService service;

    public AtmFinderController(AtmFinderService service) {
        this.service = service;
    }

    @PostMapping("/all-atm")
    public List<AtmResponse> getAllAtmWithAllInfo(@Valid @RequestBody SearchRequest searchRequest) {
        log.info("Post http request through /all-atm url path.");
        return service.getAllAtmWithAllInfo(searchRequest);
    }

    @GetMapping("/atm-location")
    public List<AtmLocationResponse> getAllAtmLocations() {
        log.info("Get http request through /atm-location url path.");
        return mapAtmDataListToAtmLocationResponseList(service.getAllAtmLocations());
    }
    
    @GetMapping("/selected-atm/{id}")
    public HttpStatus writeSelectedAtm(@PathVariable String id) {
        log.info("Get http request through /selected-atm/{id} url path.");
        service.writeSelectedAtm(id);
        return HttpStatus.OK;
    }



}
