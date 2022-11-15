package com.example.springdataautomappingobjects_exercise.services.user;

import com.example.springdataautomappingobjects_exercise.entities.User;

import java.util.Set;

public interface UserService {

    void registerUser(String[] input);

    void logInUser(String[] input);

    void logOutUser();

    User getLogInUser();

    void purchaseGame(String[] input);

    Set<String> getOwnedGames();
}
