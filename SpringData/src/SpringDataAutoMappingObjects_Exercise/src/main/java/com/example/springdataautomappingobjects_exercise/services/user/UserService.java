package com.example.springdataautomappingobjects_exercise.services.user;

import com.example.springdataautomappingobjects_exercise.entities.User;

public interface UserService {

    void registerUser(String[] input);

    void logInUser(String[] input);

    void logOutUser();

    User getLogInUser();
}
