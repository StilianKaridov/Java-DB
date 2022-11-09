package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final IngredientService ingredientService;
    private final ShampooService shampooService;
    private final Scanner scanner;

    @Autowired
    public ConsoleRunner(IngredientService ingredientService, ShampooService shampooService) {
        this.ingredientService = ingredientService;
        this.shampooService = shampooService;
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void run(String... args) throws Exception {
        //1.
//        selectShampoosBySize();

        //2.
//        selectShampoosBySizeOrLabelId();

        //3.
//        selectShampoosByPrice();

        //4.
//        selectIngredientsByName();

        //5.
//        selectIngredientsByNames();

        //6.
//        countShampoosByPrice();

        //7.
//        selectShampoosByIngredientsNames();

        //8.
//        selectShampoosByIngredientsCountLessThan();

        //9.
//        deleteIngredientByName();

        //10.
//        updateIngredientsPrice();

        //11.
//        updateIngredientsPriceByNames();
    }

    private void selectShampoosBySize() {
        String getSize = this.scanner.nextLine();

        Size size = Size.valueOf(getSize);

        this.shampooService
                .getShampoosBySizeOrderedById(size)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void selectShampoosBySizeOrLabelId() {
        String getSize = this.scanner.nextLine();
        Size size = Size.valueOf(getSize);

        Long id = scanner.nextLong();

        this.shampooService
                .getShampoosBySizeOrLabelIdOrderedByPrice(size, id)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void selectShampoosByPrice() {
        BigDecimal price = this.scanner.nextBigDecimal();

        this.shampooService
                .getShampoosWithPriceHigherThan(price)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void selectIngredientsByName() {
        String pattern = this.scanner.nextLine();

        this.ingredientService
                .getIngredientsByNameStartingWith(pattern)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void selectIngredientsByNames() {
        Set<String> names = new HashSet<>();

        String currentName = this.scanner.nextLine();

        while (!currentName.isBlank()) {
            names.add(currentName);

            currentName = scanner.nextLine();
        }

        this.ingredientService
                .getIngredientsByNames(names)
                .forEach(System.out::println);

        this.scanner.close();
    }

    private void countShampoosByPrice() {
        BigDecimal price = this.scanner.nextBigDecimal();

        System.out.println(this.shampooService
                .getCountByPriceLessThan(price));

        this.scanner.close();
    }

    private void selectShampoosByIngredientsNames() {
        Set<String> ingredients = new HashSet<>();

        String currentName = this.scanner.nextLine();

        while (!currentName.isBlank()) {
            ingredients.add(currentName);

            currentName = this.scanner.nextLine();
        }

        this.shampooService
                .getShampoosByIngredients(ingredients)
                .forEach(s -> System.out.println(s.getBrand()));

        this.scanner.close();
    }

    private void selectShampoosByIngredientsCountLessThan() {
        Integer count = Integer.parseInt(this.scanner.nextLine());

        this.shampooService
                .getShampoosByIngredientsCountLessThan(count)
                .forEach(s -> System.out.println(s.getBrand()));

        this.scanner.close();
    }

    private void deleteIngredientByName() {
        String name = this.scanner.nextLine();

        this.ingredientService.deleteIngredientByName(name);

        this.scanner.close();
    }

    private void updateIngredientsPrice() {
        this.ingredientService.updatePrice();
    }

    private void updateIngredientsPriceByNames() {
        Set<String> names = new HashSet<>();

        String currentName = this.scanner.nextLine();

        while (!currentName.isBlank()) {
            names.add(currentName);

            currentName = this.scanner.nextLine();
        }

        this.ingredientService.updatePriceByNames(names);

        this.scanner.close();
    }
}
