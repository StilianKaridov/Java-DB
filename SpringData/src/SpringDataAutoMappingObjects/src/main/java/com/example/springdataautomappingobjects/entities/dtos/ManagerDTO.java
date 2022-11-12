package com.example.springdataautomappingobjects.entities.dtos;

import com.example.springdataautomappingobjects.entities.Employee;

import java.util.List;

public class ManagerDTO {

    private String firstName;
    private String lastName;
    private List<Employee> employees;

    public ManagerDTO() {
    }

    public ManagerDTO(String firstName, String lastName, List<Employee> employees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employees = employees;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
