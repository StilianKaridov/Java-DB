package com.example.jsonprocessing.domain.dtos.product;

import java.math.BigDecimal;

public class ProductBasicInfo {

    private String name;

    private BigDecimal price;

    public ProductBasicInfo() {
    }

    public ProductBasicInfo(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
