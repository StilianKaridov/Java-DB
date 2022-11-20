package com.example.jsonprocessing.domain.dtos.user;

import com.example.jsonprocessing.domain.dtos.product.ProductsSoldWithCountDTO;

import java.util.List;

public class UserWithProductsDTO {

    private String firstName;

    private String lastName;

    private Integer age;

    private List<ProductsSoldWithCountDTO> soldProducts;

    public UserWithProductsDTO() {
    }

    public UserWithProductsDTO(String firstName, String lastName, Integer age, List<ProductsSoldWithCountDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<ProductsSoldWithCountDTO> getProducts() {
        return soldProducts;
    }

    public void setProduct(List<ProductsSoldWithCountDTO> product) {
        this.soldProducts = product;
    }
}
