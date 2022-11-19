package com.example.jsonprocessing.services.seed;

import java.io.IOException;

public interface SeedService {

    void seedUsers() throws IOException;

    void seedProducts() throws IOException;

    void seedCategories() throws IOException;
}
