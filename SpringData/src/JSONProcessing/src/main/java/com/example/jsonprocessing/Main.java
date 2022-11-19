package com.example.jsonprocessing;

import com.example.jsonprocessing.services.seed.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Main implements CommandLineRunner {

    private final SeedService seedService;

    @Autowired
    public Main(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws IOException {
        importUsers();
        importCategories();
        importProducts();
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
}
