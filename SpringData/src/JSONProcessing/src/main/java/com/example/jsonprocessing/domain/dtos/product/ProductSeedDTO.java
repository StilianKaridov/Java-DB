package com.example.jsonprocessing.domain.dtos.product;

import com.example.jsonprocessing.domain.entities.Category;
import com.example.jsonprocessing.domain.entities.User;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductSeedDTO {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    private User buyer;

    private User seller;

    private List<Category> categories;

    public ProductSeedDTO() {
        this.categories = new ArrayList<>();
    }

    public ProductSeedDTO(String name, BigDecimal price, User buyer, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.buyer = buyer;
        this.categories = categories;
    }

    public ProductSeedDTO(String name, BigDecimal price, User buyer, User seller, List<Category> categories) {
        this(name, price, buyer, categories);
        this.seller = seller;
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

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
