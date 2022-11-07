package com.example.springdataintro_exercise.domain.enums;

import java.time.LocalDate;

public enum Constant {
    ;

    public static final String BOOKS_FILE_PATH = "C:\\Users\\stili\\Desktop\\Java-DB\\SpringData\\src\\SpringDataIntro_Exercise\\src\\main\\resources\\files\\books.txt";

    public static final String AUTHORS_FILE_PATH = "C:\\Users\\stili\\Desktop\\Java-DB\\SpringData\\src\\SpringDataIntro_Exercise\\src\\main\\resources\\files\\authors.txt";

    public static final String CATEGORIES_FILE_PATH = "C:\\Users\\stili\\Desktop\\Java-DB\\SpringData\\src\\SpringDataIntro_Exercise\\src\\main\\resources\\files\\categories.txt";

    public static final LocalDate YEAR_AFTER = LocalDate.of(1999, 12, 31);

    public static final LocalDate YEAR_BEFORE = LocalDate.of(1991, 12, 31);

    public static final String AUTHOR_FIRST_NAME = "George";

    public static final String AUTHOR_LAST_NAME = "Powell";
}
