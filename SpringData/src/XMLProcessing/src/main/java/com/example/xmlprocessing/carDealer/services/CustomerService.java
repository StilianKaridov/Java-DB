package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.entities.Customer;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {

    void seedCustomers() throws IOException, JAXBException, InstantiationException, IllegalAccessException;

    Customer getRandomCustomer();
}
