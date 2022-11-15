package com.example.springdataautomappingobjects_exercise.services.user;

import com.example.springdataautomappingobjects_exercise.entities.Game;
import com.example.springdataautomappingobjects_exercise.entities.User;
import com.example.springdataautomappingobjects_exercise.entities.dtos.RegisterUserDTO;
import com.example.springdataautomappingobjects_exercise.repositories.GameRepository;
import com.example.springdataautomappingobjects_exercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.springdataautomappingobjects_exercise.constants.Messages.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private User logInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void registerUser(String[] input) {

        String email = input[1];
        String password = input[2];
        String confirmPassword = input[3];
        String fullName = input[4];

        RegisterUserDTO userDTO;

        try {
            userDTO = new RegisterUserDTO(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (this.userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(USER_ALREADY_EXISTS);
        }

        User user = userDTO.mapToUser();

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.save(user);
        System.out.printf(SUCCESSFULLY_REGISTERED_USER, user.getFullName());
    }

    @Override
    public void logInUser(String[] input) {

        String email = input[1];
        String password = input[2];

        this.logInUser = this.userRepository.getUserByEmailAndPassword(email, password);

        if (this.logInUser == null) {
            throw new IllegalArgumentException(INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
        }

        System.out.printf(SUCCESSFULLY_LOGGED_IN_USER_MESSAGE, this.logInUser.getFullName());
    }

    @Override
    public void logOutUser() {
        if (this.logInUser == null) {
            throw new IllegalStateException(CANNOT_LOGOUT_USER_MESSAGE);
        }

        System.out.printf(SUCCESSFULLY_LOGOUT_USER_MESSAGE, this.logInUser.getFullName());

        this.logInUser = null;
    }

    @Override
    public User getLogInUser() {
        return logInUser;
    }

    @Override
    public void purchaseGame(String[] input) {
        String title = input[1];

        Optional<Game> optional = this.gameRepository.findGameByTitle(title);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException(NO_GAME_TITLE);
        }

        Game game = optional.get();

        this.logInUser.purchaseGame(game);
    }

    @Override
    public Set<String> getOwnedGames() {
        Set<String> gameTitles = new HashSet<>();

        for (Game g : this.logInUser.getGames()) {
            gameTitles.add(g.getTitle());
        }

        return gameTitles;
    }
}
