package com.example.jsonprocessing.services.user;

import com.example.jsonprocessing.domain.dtos.user.UserSoldProductDTO;
import com.example.jsonprocessing.domain.dtos.user.UserWithProductsWrapperDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserSoldProductDTO> usersWithMoreThanOneProductSold();

    UserWithProductsWrapperDTO usersAndProducts() throws IOException;
}
