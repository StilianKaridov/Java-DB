package com.example.xmlprocessing;

import com.example.xmlprocessing.carDealer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component
public class CarDealerMain implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public CarDealerMain(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSales();
    }

    private void seedSuppliers() throws JAXBException, IOException {
        this.supplierService.seedSuppliers();
    }

    private void seedParts() throws JAXBException, IOException {
        this.partService.seedParts();
    }

    private void seedCars() throws JAXBException, IOException {
        this.carService.seedCars();
    }

    private void seedCustomers() throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        this.customerService.seedCustomers();
    }

    private void seedSales() {
        this.saleService.seedSales();
    }
}
