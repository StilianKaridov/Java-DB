package com.example.springdataautomappingobjects_exercise.services.game;

import com.example.springdataautomappingobjects_exercise.entities.Game;
import com.example.springdataautomappingobjects_exercise.entities.dtos.GameDTO;
import com.example.springdataautomappingobjects_exercise.repositories.GameRepository;
import com.example.springdataautomappingobjects_exercise.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.springdataautomappingobjects_exercise.constants.Messages.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public void addGame(String[] input) {
        checkIfDateIsGiven(input);

        checkIfUserIsLogged();
        checkIfUserIsAdmin();

        String title = input[1];
        BigDecimal price = new BigDecimal(input[2]);
        float size = Float.parseFloat(input[3]);
        String trailer = input[4];
        String thumbnailURL = input[5];
        String description = input[6];
        LocalDate releaseDate = LocalDate.parse(input[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        GameDTO gameDTO;

        try {
            gameDTO = new GameDTO(title, price, size, trailer, thumbnailURL, description, releaseDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        ModelMapper mapper = new ModelMapper();

        Game game = mapper.map(gameDTO, Game.class);

        this.gameRepository.save(game);

        System.out.printf(SUCCESSFULLY_ADDED_GAME, game.getTitle());
    }

    @Override
    public void editGame(String[] input) {
        checkIfUserIsLogged();
        checkIfUserIsAdmin();

        long id = Long.parseLong(input[1]);

        Optional<Game> optional = this.gameRepository.findById(id);

        if (optional.isEmpty()) {
            throw new IllegalStateException(INVALID_GAME_ID_MESSAGE);
        }

        Game game = optional.get();

        ModelMapper mapper = new ModelMapper();

        GameDTO gameDTO = mapper.map(game, GameDTO.class);

        for (int i = 2; i < input.length; i++) {
            String[] propertyValuePair = input[i].split("=");

            String propertyName = propertyValuePair[0];
            String value = propertyValuePair[1];

            try {
                switch (propertyName) {
                    case "title" -> gameDTO.setTitle(value);
                    case "price" -> gameDTO.setPrice(new BigDecimal(value));
                    case "size" -> gameDTO.setSize(Float.parseFloat(value));
                    case "trailer" -> gameDTO.setTrailer(value);
                    case "imageURL" -> gameDTO.setImageURL(value);
                    case "description" -> gameDTO.setDescription(value);
                    case "releaseDate" ->
                            gameDTO.setReleaseDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }

        }

        game = mapper.map(gameDTO, Game.class);
        game.setId(id);

        this.gameRepository.save(game);

        System.out.printf(SUCCESSFULLY_EDITED_GAME, game.getTitle());
    }

    @Override
    public void deleteGame(String[] input) {
        checkIfUserIsLogged();
        checkIfUserIsAdmin();

        long id = Long.parseLong(input[1]);

        Optional<Game> optional = this.gameRepository.findById(id);

        if (optional.isEmpty()) {
            throw new IllegalStateException(INVALID_GAME_ID_MESSAGE);
        }

        Game game = optional.get();

        this.gameRepository.delete(game);

        System.out.printf(SUCCESSFULLY_DELETED_GAME, game.getTitle());
    }

    @Override
    public List<String> getAllGamesTitleAndPrice() {
        return this.gameRepository.getAllGamesTitleAndPrice();
    }

    @Override
    public String getGameDetailsByTitle(String[] input) {
        String title = input[1];

        Optional<Game> game = this.gameRepository.findGameByTitle(title);

        if (game.isEmpty()) {
            throw new IllegalStateException(NO_GAME_TITLE);
        }

        return game.toString();
    }

    private void checkIfUserIsLogged() {
        if (this.userService.getLogInUser() == null) {
            throw new IllegalStateException(USER_NOT_LOGGED_IN_MESSAGE);
        }
    }

    private void checkIfUserIsAdmin() {
        if (!this.userService.getLogInUser().isAdmin()) {
            throw new IllegalStateException(USER_NOT_ADMIN_MESSAGE);
        }
    }

    private void checkIfDateIsGiven(String[] input) {
        if (input.length < 8) {
            throw new IllegalStateException(DATE_NOT_GIVEN_MESSAGE);
        }
    }
}
