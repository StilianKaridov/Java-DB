package com.example.xmlprocessing.carDealer.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {

    void seedSuppliers() throws JAXBException, IOException;
}
