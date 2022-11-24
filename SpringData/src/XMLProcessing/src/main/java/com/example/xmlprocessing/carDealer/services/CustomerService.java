package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerSalesDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CustomerService {

    void seedCustomers() throws IOException, JAXBException, InstantiationException, IllegalAccessException;

    Customer getRandomCustomer();

    Set<CustomerDTO> orderedCustomers() throws IOException, JAXBException;

    List<CustomerSalesDTO> customersWithAtLeastOneBoughtCar() throws IOException, JAXBException;
}
