package otp.junctionx.atm.finder.util;

import otp.junctionx.atm.finder.dto.helper.AtmData;
import otp.junctionx.atm.finder.dto.AtmLocationResponse;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    private MapperUtil() {
    }

    public static List<AtmLocationResponse> mapAtmDataListToAtmLocationResponseList(List<AtmData> atmDataList) {
        List<AtmLocationResponse> atmLocationResponseList = new ArrayList<>();
        for (AtmData atmData : atmDataList) {
            AtmLocationResponse response = new AtmLocationResponse();

            if (atmData.coord != null) {
                response.coord = atmData.coord;
            }
            if (atmData.deposit != null) {
                response.isDepositAvailable = atmData.deposit;
            }
            if (atmData.id != null) {
                response.id = atmData.id;
            }

            atmLocationResponseList.add(response);
        }
        return atmLocationResponseList;
    }

}
