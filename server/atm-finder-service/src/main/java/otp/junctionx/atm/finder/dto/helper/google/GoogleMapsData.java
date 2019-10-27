package otp.junctionx.atm.finder.dto.helper.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import otp.junctionx.atm.finder.dto.helper.google.Row;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleMapsData {

    @JsonProperty("destination_addresses")
    public List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    public List<String> originAddresses;

    public List<Row> rows;

    public String status;

}
