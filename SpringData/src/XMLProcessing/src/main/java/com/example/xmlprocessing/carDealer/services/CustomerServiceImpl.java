package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerSeedWrapperDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import com.example.xmlprocessing.carDealer.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.carDealer.constants.FilePaths.CUSTOMERS_XML_PATH;

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
}
