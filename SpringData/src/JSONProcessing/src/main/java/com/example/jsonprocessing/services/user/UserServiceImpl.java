package com.example.jsonprocessing.services.user;

import com.example.jsonprocessing.domain.dtos.user.UserSoldProductDTO;
import com.example.jsonprocessing.domain.dtos.user.UserWithProductsDTO;
import com.example.jsonprocessing.domain.dtos.user.UserWithProductsWrapperDTO;
import com.example.jsonprocessing.domain.entities.Product;
import com.example.jsonprocessing.domain.entities.User;
import com.example.jsonprocessing.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserSoldProductDTO> usersWithMoreThanOneProductSold() {
        return this.userRepository
                .findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerFirstName()
                .stream()
                .map(u -> mapper.map(u, UserSoldProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserWithProductsWrapperDTO usersAndProducts() throws IOException {
        List<UserWithProductsDTO> usersAndProducts = this.userRepository
                .findAllBySoldProductsBuyerIsNotNullOrderBySoldProductsBuyerFirstName()
                .stream()
                .map(user->mapper.map(user, UserWithProductsDTO.class))
                .collect(Collectors.toList());

        return new UserWithProductsWrapperDTO(usersAndProducts);
    }
}
