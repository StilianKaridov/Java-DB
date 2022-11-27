package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import softuni.exam.models.enums.ApartmentType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentImportDTO {

    @XmlElement
    private ApartmentType apartmentType;

    @XmlElement
    private double area;

    @XmlElement
    private String town;

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public void setArea(double area) {
        if (area < 40) {
            throw new IllegalArgumentException();
        }

        this.area = area;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public boolean validate() {
        try {
            setArea(this.area);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
