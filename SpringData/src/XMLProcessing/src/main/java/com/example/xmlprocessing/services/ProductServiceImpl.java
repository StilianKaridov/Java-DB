package com.example.xmlprocessing.services;

import com.example.xmlprocessing.domain.dtos.product.ProductInRangeDTO;
import com.example.xmlprocessing.domain.dtos.product.ProductInRangeWrapperDTO;
import com.example.xmlprocessing.domain.dtos.product.ProductSeedWrapperDTO;
import com.example.xmlprocessing.domain.entities.Product;
import com.example.xmlprocessing.domain.entities.User;
import com.example.xmlprocessing.repositories.CategoryRepository;
import com.example.xmlprocessing.repositories.ProductRepository;
import com.example.xmlprocessing.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.constants.FilePaths.PRODUCTS_IN_RANGE_PATH;
import static com.example.xmlprocessing.constants.FilePaths.PRODUCTS_PATH;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() == 0) {
            FileReader reader = new FileReader(PRODUCTS_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(ProductSeedWrapperDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ProductSeedWrapperDTO productWrapperDTO= (ProductSeedWrapperDTO) unmarshaller.unmarshal(reader);

            List<Product> productsToInsert = productWrapperDTO.getProducts()
                    .stream()
                    .map(p -> mapper.map(p, Product.class))
                    .collect(Collectors.toList());

            Random random = new Random();

            for (Product p : productsToInsert) {

                long id = random.nextInt(55);

                Optional<User> byId = this.userRepository.findById(id + 1);
                p.setSeller(byId.get());

                id = random.nextInt(99);

                Optional<User> buyer = this.userRepository.findById(id + 1);
                p.setBuyer(buyer.orElse(null));

                p.setCategories(this.categoryRepository.getRandomCategories());
            }

            this.productRepository.saveAllAndFlush(productsToInsert);

            reader.close();
        }
    }

    @Override
    public List<ProductInRangeDTO> productsInRange(BigDecimal lowPrice, BigDecimal highPrice) throws JAXBException {
        List<ProductInRangeDTO> products = this.productRepository
                .findAllByPriceBetweenAndBuyerNullOrderByPrice(lowPrice, highPrice)
                .stream()
                .map(p->mapper.map(p, ProductInRangeDTO.class))
                .collect(Collectors.toList());

        ProductInRangeWrapperDTO wrapperDTO = new ProductInRangeWrapperDTO(products);

        JAXBContext jaxbContext = JAXBContext.newInstance(ProductInRangeWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(PRODUCTS_IN_RANGE_PATH);

        marshaller.marshal(wrapperDTO, file);

        return products;
    }
}
