package otp.junctionx.atm.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import otp.junctionx.atm.finder.dto.helper.Coord;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotNull
    public Coord coord;

    @NotNull
    public String dayName;

    @NotNull
    public int section;

    @NotNull
    public boolean isDepositRequired;

}
