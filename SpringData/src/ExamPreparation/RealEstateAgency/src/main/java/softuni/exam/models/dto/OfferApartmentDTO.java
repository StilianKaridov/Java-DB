package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferApartmentDTO {

    @XmlElement
    private Long id;
}
