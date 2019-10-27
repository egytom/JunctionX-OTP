package otp.junctionx.atm.finder.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import otp.junctionx.atm.finder.dto.AtmResponse;
import otp.junctionx.atm.finder.dto.SearchRequest;
import otp.junctionx.atm.finder.dto.helper.Coord;
import otp.junctionx.atm.finder.model.Atm;
import otp.junctionx.atm.finder.repository.AtmRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtmFinderTest {

    @InjectMocks
    private AtmFinderService service;

    @Mock
    private AtmRepository repository;

    @BeforeEach
    void init() {
        this.repository = mock(AtmRepository.class);
        this.service = new AtmFinderService(repository);
    }

    @Test
    public void getAllAtmWithAllInfoDepositTrueTest() {
        SearchRequest searchRequest = getSearchRequest(true);
        List<Atm> atmList = getAtmList();
        when(repository.findAll()).thenReturn(atmList);

        List<AtmResponse> result = service.getAllAtmWithAllInfo(searchRequest);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(true, result.get(0).isDepositAvailable);
        assert(result.get(0).queueAndTravelTime.contains("min"));
    }

    @Test
    public void getAllAtmWithAllInfoDepositFalseTest() {
        SearchRequest searchRequest = getSearchRequest(false);
        List<Atm> atmList = getAtmList();
        when(repository.findAll()).thenReturn(atmList);

        List<AtmResponse> result = service.getAllAtmWithAllInfo(searchRequest);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(false, result.get(0).isDepositAvailable);
        assert(result.get(0).queueAndTravelTime.contains("min"));
    }

    private SearchRequest getSearchRequest(Boolean isDepositRequired) {
        return SearchRequest.builder()
                .coord(Coord.builder().x(47.4648836).y(19.0228743).build())
                .dayName("SATURDAY")
                .isDepositRequired(isDepositRequired)
                .section(20)
                .build();
    }

    private List<Atm> getAtmList() {
        List<Atm> atmList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            atmList.add(new Atm(Integer.toString(i), 1));
        }
        return atmList;
    }

}
