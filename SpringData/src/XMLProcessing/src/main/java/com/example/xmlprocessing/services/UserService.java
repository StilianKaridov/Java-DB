package com.example.xmlprocessing.services;

import com.example.xmlprocessing.domain.dtos.user.UserDTO;
import com.example.xmlprocessing.domain.dtos.user.UserWithAttributesDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers() throws IOException, JAXBException;

    List<UserDTO> usersWithMoreThanOneProductSold() throws JAXBException;

    List<UserWithAttributesDTO> usersWithProducts() throws JAXBException;
}
