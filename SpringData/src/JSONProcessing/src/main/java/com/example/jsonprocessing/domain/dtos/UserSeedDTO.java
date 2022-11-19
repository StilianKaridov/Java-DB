package com.example.jsonprocessing.domain.dtos;

import com.google.gson.annotations.Expose;

public class UserSeedDTO {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    public UserSeedDTO() {
    }

    public UserSeedDTO(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
