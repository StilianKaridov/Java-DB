package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.enums.DayOfWeek;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDTO {

    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @XmlElement(name = "max_temperature")
    private double maxTemperature;

    @XmlElement(name = "min_temperature")
    private double minTemperature;

    @XmlElement
    private String sunrise;

    @XmlElement
    private String sunset;

    @XmlElement
    private Long city;

    public void setMaxTemperature(double maxTemperature) {
        if (maxTemperature < -20 || maxTemperature > 60) {
            throw new IllegalArgumentException();
        }

        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        if (minTemperature < -50 || minTemperature > 40) {
            throw new IllegalArgumentException();
        }

        this.minTemperature = minTemperature;
    }

    public boolean validate() {
        try {
            setMaxTemperature(this.maxTemperature);
            setMinTemperature(this.minTemperature);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
