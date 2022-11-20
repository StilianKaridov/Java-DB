package com.example.jsonprocessing.services.product;

import com.example.jsonprocessing.domain.dtos.product.ProductInRangeDTO;
import com.example.jsonprocessing.domain.entities.Product;
import com.example.jsonprocessing.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductInRangeDTO> getAllInRangeWithoutBuyer(BigDecimal low, BigDecimal high) {

        TypeMap<Product, ProductInRangeDTO> typeMap = mapper.createTypeMap(Product.class, ProductInRangeDTO.class);

        typeMap.addMappings(m -> m.map(Product::getSellerFullName,
                ProductInRangeDTO::setSeller));

        return this.productRepository.findByPriceBetweenAndBuyerNullOrderByPrice(low, high)
                .stream()
                .map(typeMap::map)
                .collect(Collectors.toList());
    }
}
