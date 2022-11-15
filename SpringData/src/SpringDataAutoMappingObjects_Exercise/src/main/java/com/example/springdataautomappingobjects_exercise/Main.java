package com.example.springdataautomappingobjects_exercise;

import com.example.springdataautomappingobjects_exercise.services.game.GameService;
import com.example.springdataautomappingobjects_exercise.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.springdataautomappingobjects_exercise.constants.Commands.*;

@Component
public class Main implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final Scanner scanner;

    @Autowired
    public Main(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {
        String[] input = scanner.nextLine().split("\\|");

        String command = input[0];

        while (!command.equals("Close")) {
            try {
                switch (command) {
                    case REGISTER_USER_COMMAND -> this.userService.registerUser(input);
                    case LOGIN_USER_COMMAND -> this.userService.logInUser(input);
                    case LOGOUT_COMMAND -> this.userService.logOutUser();
                    case ADD_GAME_COMMAND -> this.gameService.addGame(input);
                    case EDIT_GAME_COMMAND -> this.gameService.editGame(input);
                    case DELETE_GAME_COMMAND -> this.gameService.deleteGame(input);
                    case SHOW_ALL_GAMES_COMMAND -> this.gameService.getAllGamesTitleAndPrice()
                            .forEach(s -> System.out.printf("%s%n",
                                    s.replace(",", " ")));
                    case DETAIL_GAME_COMMAND -> System.out.println(this.gameService.getGameDetailsByTitle(input));
                    case OWNED_GAMES_COMMAND -> this.userService.getOwnedGames()
                            .forEach(System.out::println);
                    case PURCHASE_GAME_COMMAND -> this.userService.purchaseGame(input);

                    default -> System.out.println(COMMAND_NOT_FOUND);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }

            input = scanner.nextLine().split("\\|");
            command = input[0];
        }

    }
}
