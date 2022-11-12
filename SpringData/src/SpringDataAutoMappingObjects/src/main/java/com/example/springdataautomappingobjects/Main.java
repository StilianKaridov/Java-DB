package com.example.springdataautomappingobjects;

import com.example.springdataautomappingobjects.entities.Employee;
import com.example.springdataautomappingobjects.entities.dtos.EmployeeDTO;
import com.example.springdataautomappingobjects.entities.dtos.ManagerDTO;
import com.example.springdataautomappingobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final ModelMapper mapper;

    @Autowired
    public Main(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.mapper = new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        //1.
//        simpleMapping();

        //2.
//        advancedMapping();

        //3.
        projection();
    }

    private void simpleMapping() {
        Employee employee = new Employee("Stilian", "Karidov", BigDecimal.TEN);

        EmployeeDTO employeeDTO = this.mapper.map(employee, EmployeeDTO.class);

        System.out.println(employeeDTO.getFirstName() + " " + employeeDTO.getLastName());
    }

    private void advancedMapping() {
        ManagerDTO dto = this.employeeService.findAllManagers(1L);
        System.out.println(dto.getFirstName() + " " +
                dto.getLastName() + " | " + "Employees: " + dto.getEmployees().size());
        dto.getEmployees()
                .forEach(e -> System.out.printf("\t - %s %s %.2f%n"
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getSalary()));
    }

    private void projection() {
        ModelMapper mapper = new ModelMapper();

        List<Employee> employees = this.employeeService.findEmployeesBefore1990(LocalDate.of(1990, 1, 1));

        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);

        typeMap.addMappings(m -> m.map(src -> src.getManager().getLastName(), EmployeeDTO::setManagerLastName));

        for (Employee e : employees) {
            EmployeeDTO map = typeMap.map(e);

            System.out.println(map.toString());
        }
    }
}
