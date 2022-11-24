package com.example.xmlprocessing.carDealer.domain.dtos.car;

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
public class CarWithPartsDTO {

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private double travelledDistance;

    @XmlElement(name = "parts")
    private PartWrapperDTO parts;
}
