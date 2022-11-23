package com.example.xmlprocessing.productShop.services;

import com.example.xmlprocessing.productShop.domain.dtos.user.UserDTO;
import com.example.xmlprocessing.productShop.domain.dtos.user.UserWithAttributesDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    void seedUsers() throws IOException, JAXBException;

    List<UserDTO> usersWithMoreThanOneProductSold() throws JAXBException;

    Set<UserWithAttributesDTO> usersWithProducts() throws JAXBException;
}
