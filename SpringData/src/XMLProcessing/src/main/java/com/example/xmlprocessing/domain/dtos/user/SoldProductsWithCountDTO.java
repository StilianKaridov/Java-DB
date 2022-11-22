package com.example.xmlprocessing.domain.dtos.user;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsWithCountDTO {

    @XmlAttribute
    private int count;

    @XmlElement(name = "product")
    private List<ProductDTO> products;

    public SoldProductsWithCountDTO() {
        this.products = new ArrayList<>();
    }

    public SoldProductsWithCountDTO(List<ProductDTO> products) {
        this.products = products;
        this.count = products.size();
    }
}
