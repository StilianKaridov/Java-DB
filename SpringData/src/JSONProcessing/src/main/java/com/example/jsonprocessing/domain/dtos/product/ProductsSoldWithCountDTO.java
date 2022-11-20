package com.example.jsonprocessing.domain.dtos.product;

import com.example.jsonprocessing.domain.dtos.product.ProductBasicInfo;

import java.util.List;

public class ProductsSoldWithCountDTO {

    private Integer count;

    private List<ProductBasicInfo> products;

    public ProductsSoldWithCountDTO() {
    }

    public ProductsSoldWithCountDTO(List<ProductBasicInfo> products) {
        this.products = products;
        this.count = products.size();
    }

    public Integer getCount() {

        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductBasicInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBasicInfo> products) {
        this.products = products;
    }
}
