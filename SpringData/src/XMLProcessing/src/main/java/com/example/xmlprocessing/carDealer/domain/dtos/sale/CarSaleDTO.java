package com.example.xmlprocessing.carDealer.domain.dtos.sale;

import com.example.xmlprocessing.carDealer.domain.entities.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSaleDTO {

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private double travelledDistance;

    @XmlTransient
    private List<Part> parts;
}
