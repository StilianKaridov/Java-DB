package com.example.xmlprocessing.services;

import com.example.xmlprocessing.domain.dtos.category.CategoryDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void seedCategories() throws IOException, JAXBException;

    List<CategoryDTO> categoriesByProductCount() throws JAXBException;
}
