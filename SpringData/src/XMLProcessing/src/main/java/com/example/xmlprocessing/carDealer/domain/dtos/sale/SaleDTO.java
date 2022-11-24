package com.example.xmlprocessing.carDealer.domain.dtos.sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDTO {

    @XmlElement
    private CarSaleDTO car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement(name = "discount")
    private double discountPercentage;

    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;
}
