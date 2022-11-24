package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface SupplierService {

    void seedSuppliers() throws JAXBException, IOException;

    List<SupplierDTO> getLocalSuppliers() throws IOException, JAXBException;
}
