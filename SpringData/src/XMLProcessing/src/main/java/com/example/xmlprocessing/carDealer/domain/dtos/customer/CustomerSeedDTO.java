package com.example.xmlprocessing.carDealer.domain.dtos.customer;

import com.example.xmlprocessing.config.DateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedDTO {

    @XmlAttribute
    private String name;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date birthDate;

    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;
}
