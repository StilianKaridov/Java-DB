package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportOfferDTO {

    private String agentFistName;

    private String agentLastName;

    private Long offerID;

    private double apartmentArea;

    private String townName;

    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №%d:%n" +
                        "   -Apartment area: %.2f%n" +
                        "   --Town: %s%n" +
                        "   ---Price: %.2f$%n",
                this.agentFistName,
                this.agentLastName,
                this.offerID,
                this.apartmentArea,
                this.townName,
                this.price.doubleValue());
    }
}
