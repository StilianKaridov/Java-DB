package com.example.xmlprocessing.carDealer.services;

import com.example.xmlprocessing.carDealer.domain.dtos.sale.SaleDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface SaleService {

    void seedSales();

    List<SaleDTO> getAllSalesWithDiscount() throws IOException, JAXBException;
}
