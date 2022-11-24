package com.example.xmlprocessing.carDealer.domain.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerDTO {

    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement(name = "birth-date")
    private Date birthDate;

    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;
}
