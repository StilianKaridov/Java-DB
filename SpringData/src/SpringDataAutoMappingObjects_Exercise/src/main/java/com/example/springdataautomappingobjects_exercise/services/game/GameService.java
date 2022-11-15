package com.example.springdataautomappingobjects_exercise.services.game;

import java.util.List;

public interface GameService {
    void addGame(String[] input);

    void editGame(String[] input);

    void deleteGame(String[] input);

    List<String> getAllGamesTitleAndPrice();

    String getGameDetailsByTitle(String[] input);
}
