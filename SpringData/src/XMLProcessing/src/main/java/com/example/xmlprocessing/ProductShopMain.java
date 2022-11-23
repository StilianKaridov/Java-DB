package com.example.xmlprocessing;

import com.example.xmlprocessing.productShop.services.CategoryService;
import com.example.xmlprocessing.productShop.services.ProductService;
import com.example.xmlprocessing.productShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

//@Component
public class ProductShopMain implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductShopMain(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedUsers();
//        seedCategories();
//        seedProducts();

//        productsInRange();

//        successfullySoldProducts();

//        categoriesByProductsCount();

//        usersWithProducts();
    }

    private void seedUsers() throws JAXBException, IOException {
        this.userService.seedUsers();
    }

    private void seedProducts() throws IOException, JAXBException {
        this.productService.seedProducts();
    }

    private void seedCategories() throws JAXBException, IOException {
        this.categoryService.seedCategories();
    }

    private void productsInRange() throws JAXBException {
        BigDecimal lowPrice = new BigDecimal(500);
        BigDecimal highPrice = new BigDecimal(1000);

        this.productService.productsInRange(lowPrice, highPrice);
    }

    private void successfullySoldProducts() throws JAXBException {
        this.userService.usersWithMoreThanOneProductSold();
    }

    private void categoriesByProductsCount() throws JAXBException {
        this.categoryService.categoriesByProductCount();
    }

    private void usersWithProducts() throws JAXBException {
        this.userService.usersWithProducts();
    }
}
