package com.example.springdataautomappingobjects.services;

import com.example.springdataautomappingobjects.entities.Employee;
import com.example.springdataautomappingobjects.entities.dtos.EmployeeDTO;
import com.example.springdataautomappingobjects.entities.dtos.ManagerDTO;
import com.example.springdataautomappingobjects.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee insertEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO findById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Employee employee = this.employeeRepository.findById(1L).orElse(null);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public ManagerDTO findAllManagers(Long id) {
        ModelMapper mapper = new ModelMapper();

        Employee employee = this.employeeRepository.findById(id).orElse(null);
        return mapper.map(employee, ManagerDTO.class);
    }

    @Override
    public List<Employee> findEmployeesBefore1990(LocalDate date) {
        return this.employeeRepository.findEmployeesByBirthdayBeforeOrderBySalaryDesc(date);
    }
}
