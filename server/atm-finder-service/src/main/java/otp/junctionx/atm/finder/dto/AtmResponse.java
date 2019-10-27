package otp.junctionx.atm.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import otp.junctionx.atm.finder.dto.helper.Address;
import otp.junctionx.atm.finder.dto.helper.Coord;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmResponse {

    public String id;
    public String date;
    public String day;
    public Boolean isDepositAvailable;
    public Address address;
    public Coord coord;
    public Integer countOfFutureCustomers;
    public String queueAndTravelTime;

}
