package ExamPreparation.CarService.src.main.java.softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ExamPreparation.CarService.src.main.java.softuni.exam.models.enums.CarType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDTO {

    @XmlElement
    private String carMake;

    @XmlElement
    private String carModel;

    @XmlElement
    private int year;

    @XmlElement
    private String plateNumber;

    @XmlElement
    private int kilometers;

    @XmlElement
    private double engine;

    @XmlElement
    private CarType carType;

    public void setCarMake(String carMake) {
        if (carMake.length() < 2 || carMake.length() > 30) {
            throw new IllegalArgumentException();
        }

        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        if (carModel.length() < 2 || carModel.length() > 30) {
            throw new IllegalArgumentException();
        }

        this.carModel = carModel;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException();
        }

        this.year = year;
    }

    public void setPlateNumber(String plateNumber) {
        if (plateNumber.length() < 2 || plateNumber.length() > 30) {
            throw new IllegalArgumentException();
        }

        this.plateNumber = plateNumber;
    }

    public void setKilometers(int kilometers) {
        if (kilometers <= 0) {
            throw new IllegalArgumentException();
        }

        this.kilometers = kilometers;
    }

    public void setEngine(double engine) {
        if (engine < 1.00) {
            throw new IllegalArgumentException();
        }

        this.engine = engine;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public boolean validate() {
        try {
            setCarMake(this.carMake);
            setCarModel(this.carModel);
            setYear(this.year);
            setPlateNumber(this.plateNumber);
            setKilometers(this.kilometers);
            setEngine(this.engine);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
