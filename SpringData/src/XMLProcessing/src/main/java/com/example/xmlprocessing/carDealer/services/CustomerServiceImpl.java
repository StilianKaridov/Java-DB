package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.customer.*;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import com.example.xmlprocessing.carDealer.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.carDealer.constants.FilePaths.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedCustomers() throws IOException, JAXBException {
        if (this.customerRepository.count() == 0) {
            FileReader fileReader = new FileReader(CUSTOMERS_XML_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(CustomerSeedWrapperDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            CustomerSeedWrapperDTO wrapperDTO = (CustomerSeedWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Customer> customers = wrapperDTO.getCustomers()
                    .stream()
                    .map(c -> mapper.map(c, Customer.class))
                    .collect(Collectors.toList());

            this.customerRepository.saveAllAndFlush(customers);

            fileReader.close();
        }
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();

        long customerId = random.nextInt(29);

        return this.customerRepository.findById(customerId + 1).get();
    }

    @Override
    public Set<CustomerDTO> orderedCustomers() throws IOException, JAXBException {
        Set<CustomerDTO> customers = this.customerRepository.getAllOrderByBirthDateAndYoungDriver();

        FileWriter fileWriter = new FileWriter(ORDERED_CUSTOMERS_PATH);

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomerWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        CustomerWrapperDTO wrapperDTO = new CustomerWrapperDTO(customers);

        marshaller.marshal(wrapperDTO, fileWriter);

        fileWriter.close();

        return customers;
    }

    @Override
    public List<CustomerSalesDTO> customersWithAtLeastOneBoughtCar() throws IOException, JAXBException {
        List<CustomerSalesDTO> customers = this.customerRepository.findAllWithAtLeastOneBoughtCar();

        FileWriter fileWriter = new FileWriter(CUSTOMER_TOTAL_SALES_PATH);

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomerSalesWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        CustomerSalesWrapperDTO wrapperDTO = new CustomerSalesWrapperDTO(customers);

        marshaller.marshal(wrapperDTO, fileWriter);

        fileWriter.close();

        return customers;
    }
}
