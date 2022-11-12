package com.example.springdataautomappingobjects.services;

import com.example.springdataautomappingobjects.entities.Employee;
import com.example.springdataautomappingobjects.entities.dtos.EmployeeDTO;
import com.example.springdataautomappingobjects.entities.dtos.ManagerDTO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    Employee insertEmployee(Employee employee);

    EmployeeDTO findById(Long id);

    ManagerDTO findAllManagers(Long id);

    List<Employee> findEmployeesBefore1990(LocalDate date);
}
