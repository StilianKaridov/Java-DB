package com.example.xmlprocessing.productShop.services;

import com.example.xmlprocessing.productShop.domain.dtos.user.*;
import com.example.xmlprocessing.productShop.domain.entities.User;
import com.example.xmlprocessing.productShop.repositories.UserRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.xmlprocessing.productShop.constants.FilePaths.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {
            FileReader reader = new FileReader(USERS_PATH);

            JAXBContext context = JAXBContext.newInstance(UserSeedWrapperDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            UserSeedWrapperDTO userWrapperDTO = (UserSeedWrapperDTO) unmarshaller.unmarshal(reader);

            List<User> usersToInsert = userWrapperDTO.getUsers()
                    .stream()
                    .map(u -> mapper.map(u, User.class))
                    .collect(Collectors.toList());

            this.userRepository.saveAllAndFlush(usersToInsert);

            reader.close();
        }
    }

    @Override
    public List<UserDTO> usersWithMoreThanOneProductSold() throws JAXBException {
        List<UserDTO> users = this.userRepository
                .findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerFirstName()
                .stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .collect(Collectors.toList());

        AllUsersWrapperDTO allUsersWrapperDTO = new AllUsersWrapperDTO(users);

        JAXBContext jaxbContext = JAXBContext.newInstance(AllUsersWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(SUCCESSFULLY_SOLD_PRODUCTS_PATH);

        marshaller.marshal(allUsersWrapperDTO, file);

        return users;
    }

    @Override
    public Set<UserWithAttributesDTO> usersWithProducts() throws JAXBException {
        Set<UserWithAttributesDTO> users = this.userRepository
                .findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerLastName()
                .stream()
                .map(u -> mapper.map(u, UserWithAttributesDTO.class))
                .collect(Collectors.toSet());

        List<UserWithAttributesDTO> sortedUsers =
                users
                        .stream()
                        .sorted((u1, u2) -> Integer.compare(u2.getSoldProducts().getCount(), u1.getSoldProducts().getCount()))
                        .collect(Collectors.toList());


        AllUsersWithCountWrapperDTO wrapperDTO = new AllUsersWithCountWrapperDTO(sortedUsers);

        JAXBContext jaxbContext = JAXBContext.newInstance(AllUsersWithCountWrapperDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(USERS_WITH_PRODUCTS_PATH);

        marshaller.marshal(wrapperDTO, file);

        return users;
    }
}
