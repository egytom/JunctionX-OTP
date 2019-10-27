package otp.junctionx.atm.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "atm")
public class Atm {

    @Id
    private String id;

    private int expectedCustomers;

    @Override
    public String toString() {
        return "Atm [id=" + id + ", expectedCustomers=" + expectedCustomers + "]";
    }

}
