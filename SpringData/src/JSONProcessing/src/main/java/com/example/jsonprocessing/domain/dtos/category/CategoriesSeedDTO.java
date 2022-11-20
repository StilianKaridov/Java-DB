package com.example.jsonprocessing.domain.dtos.category;

import com.google.gson.annotations.Expose;

public class CategoriesSeedDTO {

    @Expose
    private String name;

    public CategoriesSeedDTO() {
    }

    public CategoriesSeedDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
