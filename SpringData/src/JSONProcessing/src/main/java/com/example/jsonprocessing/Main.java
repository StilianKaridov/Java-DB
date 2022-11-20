package com.example.jsonprocessing;

import com.example.jsonprocessing.domain.dtos.category.CategoryDTO;
import com.example.jsonprocessing.domain.dtos.product.ProductInRangeDTO;
import com.example.jsonprocessing.domain.dtos.user.UserSoldProductDTO;
import com.example.jsonprocessing.domain.dtos.user.UserWithProductsWrapperDTO;
import com.example.jsonprocessing.services.category.CategoryService;
import com.example.jsonprocessing.services.product.ProductService;
import com.example.jsonprocessing.services.seed.SeedService;
import com.example.jsonprocessing.services.user.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.example.jsonprocessing.constants.FilePaths.FilePaths.*;

@Component
public class Main implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final Scanner scanner;
    private final Gson gson;

    @Autowired
    public Main(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService, Scanner scanner, Gson gson) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.scanner = scanner;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws IOException {
//        importUsers();
//        importCategories();
//        importProducts();

        //Query 1
//        productsInRange();

        //Query 2
//        usersSoldProducts();

        //Query 3
//        categoriesByProducts();

        //Query 4
        usersWithProducts();
    }

    private void importUsers() throws IOException {
        this.seedService.seedUsers();
    }

    private void importProducts() throws IOException {
        this.seedService.seedProducts();
    }

    private void importCategories() throws IOException {
        this.seedService.seedCategories();
    }

    private void productsInRange() throws IOException {
        BigDecimal lowPrice = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));

        BigDecimal highPrice = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));

        List<ProductInRangeDTO> allInRangeWithoutBuyer = this.productService.getAllInRangeWithoutBuyer(lowPrice, highPrice);

        this.writeFileToJson(allInRangeWithoutBuyer, PRODUCTS_IN_RANGE_PATH);
    }

    private void usersSoldProducts() throws IOException {
        List<UserSoldProductDTO> users = this.userService.usersWithMoreThanOneProductSold();

        this.writeFileToJson(users, SUCCESSFULLY_SOLD_PRODUCTS_PATH);
    }

    private void categoriesByProducts() throws IOException {
        Set<CategoryDTO> categories = this.categoryService.getAllCategories();

        this.writeFileToJson(categories, CATEGORIES_BY_PRODUCTS_PATH);
    }

    private void usersWithProducts() throws IOException {
        UserWithProductsWrapperDTO user = this.userService.usersAndProducts();

        this.writeFileToJson(user, USERS_WITH_PRODUCTS_PATH);
    }

    private void writeFileToJson(Object src, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        this.gson.toJson(src, writer);

        writer.flush();
        writer.close();
    }
}
