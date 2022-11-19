package com.example.jsonprocessing.services.seed;

import com.example.jsonprocessing.domain.dtos.CategoriesSeedDTO;
import com.example.jsonprocessing.domain.dtos.ProductSeedDTO;
import com.example.jsonprocessing.domain.dtos.UserSeedDTO;
import com.example.jsonprocessing.domain.entities.Category;
import com.example.jsonprocessing.domain.entities.Product;
import com.example.jsonprocessing.domain.entities.User;
import com.example.jsonprocessing.repositories.CategoryRepository;
import com.example.jsonprocessing.repositories.ProductRepository;
import com.example.jsonprocessing.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.jsonprocessing.constants.FilePaths.FilePaths.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper mapper;


    @Autowired
    public SeedServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0) {
            FileReader reader = new FileReader(USERS_PATH);

            UserSeedDTO[] users = this.gson.fromJson(reader, UserSeedDTO[].class);

            List<User> usersToInsert = Arrays.stream(users)
                    .map(u -> mapper.map(u, User.class))
                    .collect(Collectors.toList());

            for (User u : usersToInsert) {
                u.setFriends(this.userRepository.getRandomFriends());
            }

            this.userRepository.saveAllAndFlush(usersToInsert);

            reader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {
            FileReader reader = new FileReader(PRODUCTS_PATH);

            ProductSeedDTO[] products = this.gson.fromJson(reader, ProductSeedDTO[].class);

            List<Product> productsToInsert = Arrays.stream(products)
                    .map(p -> mapper.map(p, Product.class))
                    .collect(Collectors.toList());

            int index = 0;

            for (Product p : productsToInsert) {
                p.setSeller(this.userRepository.getRandomUser());
                p.setBuyer(setNullBuyerForEveryTenthProduct(index));
                p.setCategories(this.categoryRepository.getRandomCategories());

                index++;
            }

            this.productRepository.saveAllAndFlush(productsToInsert);

            reader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            FileReader reader = new FileReader(CATEGORIES_PATH);

            CategoriesSeedDTO[] categories = this.gson.fromJson(reader, CategoriesSeedDTO[].class);

            List<Category> categoriesToInsert = Arrays.stream(categories)
                    .map(c -> mapper.map(c, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categoriesToInsert);

            reader.close();
        }
    }

    private User setNullBuyerForEveryTenthProduct(int index) {
        if (index % 10 != 0) {
            return this.userRepository.getRandomUser();
        }

        return null;
    }
}
