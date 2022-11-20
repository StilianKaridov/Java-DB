package com.example.jsonprocessing.services.product;

import com.example.jsonprocessing.domain.dtos.product.ProductInRangeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductInRangeDTO> getAllInRangeWithoutBuyer(BigDecimal low, BigDecimal high);
}
