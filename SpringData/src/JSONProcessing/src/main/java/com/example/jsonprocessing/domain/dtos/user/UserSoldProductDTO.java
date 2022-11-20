package com.example.jsonprocessing.domain.dtos.user;

import com.example.jsonprocessing.domain.dtos.product.SoldProductDTO;
import com.example.jsonprocessing.domain.entities.Product;

import java.util.List;

public class UserSoldProductDTO {

    private String firstName;

    private String lastName;

    private List<SoldProductDTO> soldProducts;

    public UserSoldProductDTO() {
    }

    public UserSoldProductDTO(String firstName, String lastName, List<SoldProductDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public List<SoldProductDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
