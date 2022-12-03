package ExamPreparation.CarService.src.main.java.softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import softuni.exam.config.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDTO {

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime date;

    @XmlElement
    private BigDecimal price;

    @XmlElement
    private CarDTO car;

    @XmlElement
    private MechanicDTO mechanic;

    @XmlElement
    private PartDTO part;

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public void setMechanic(MechanicDTO mechanic) {
        this.mechanic = mechanic;
    }

    public void setPart(PartDTO part) {
        this.part = part;
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
