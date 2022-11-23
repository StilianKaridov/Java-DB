package com.example.xmlprocessing.productShop.services;

import com.example.xmlprocessing.productShop.domain.dtos.product.ProductInRangeDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts() throws IOException, JAXBException;

    List<ProductInRangeDTO> productsInRange(BigDecimal lowPrice, BigDecimal highPrice) throws JAXBException;
}
