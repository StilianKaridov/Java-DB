package ExamPreparation.CarService.src.main.java.softuni.exam.util;

import java.nio.file.Path;

public enum FilePaths {
    ;

    public static final Path PARTS_PATH = Path.of("src/main/resources/files/json/parts.json");

    public static final Path MECHANICS_PATH = Path.of("src/main/resources/files/json/mechanics.json");

    public static final Path CARS_PATH = Path.of("src/main/resources/files/xml/cars.xml");

    public static final Path TASKS_PATH = Path.of("src/main/resources/files/xml/tasks.xml");
}
