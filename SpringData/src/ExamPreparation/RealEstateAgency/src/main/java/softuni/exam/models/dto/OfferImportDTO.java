package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import softuni.exam.config.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @XmlElement
    private BigDecimal price;

    @XmlElement
    private OfferAgentDTO agent;

    @XmlElement
    private OfferApartmentDTO apartment;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate publishedOn;

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    public void setAgent(OfferAgentDTO agent) {
        this.agent = agent;
    }

    public void setApartment(OfferApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public void setPublished_on(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public boolean validate() {
        try {
            setPrice(this.price);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
