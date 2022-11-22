package com.example.xmlprocessing.services;

import com.example.xmlprocessing.domain.dtos.category.CategoryDTO;
import com.example.xmlprocessing.domain.dtos.category.CategorySeedWrapperDTO;
import com.example.xmlprocessing.domain.dtos.category.CategoryWrapperDTO;
import com.example.xmlprocessing.domain.entities.Category;
import com.example.xmlprocessing.repositories.CategoryRepository;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.constants.FilePaths.CATEGORIES_BY_PRODUCTS_PATH;
import static com.example.xmlprocessing.constants.FilePaths.CATEGORIES_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            FileReader reader = new FileReader(CATEGORIES_PATH);

            JAXBContext jaxbContext = JAXBContext.newInstance(CategorySeedWrapperDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            CategorySeedWrapperDTO categories = (CategorySeedWrapperDTO) unmarshaller.unmarshal(reader);

            List<Category> categoriesToInsert = categories.getCategories()
                    .stream()
                    .map(c -> mapper.map(c, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categoriesToInsert);

            reader.close();
        }
    }

    @Override
    public List<CategoryDTO> categoriesByProductCount() throws JAXBException {
        List<CategoryDTO> categories = this.categoryRepository
                .getAllOrderByProductsSize()
                .stream()
                .map(c->mapper.map(c, CategoryDTO.class))
                .collect(Collectors.toList());

        CategoryWrapperDTO wrapperDTO = new CategoryWrapperDTO(categories);

        JAXBContext jaxbContext = JAXBContext.newInstance(CategoryWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(CATEGORIES_BY_PRODUCTS_PATH);

        marshaller.marshal(wrapperDTO, file);

        return categories;
    }
}
