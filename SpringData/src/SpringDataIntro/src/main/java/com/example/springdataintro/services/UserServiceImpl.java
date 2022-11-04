package com.example.springdataintro.services;

import com.example.springdataintro.models.User;
import com.example.springdataintro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> userByUsername = userRepository.getUserByUsername(user.getUsername());

        if (userByUsername.isPresent()) {
            throw new RuntimeException("Sorry, user exists!");
        }

        this.userRepository.save(user);
    }
}
