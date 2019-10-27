package otp.junctionx.atm.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import otp.junctionx.atm.finder.dto.helper.Coord;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmLocationResponse {

    public String id;
    public Coord coord;
    public boolean isDepositAvailable;

}
