package com.example.springdataautomappingobjects_exercise.constants;

public enum RegExpValidations {
    ;

    public static final String EMAIL_VALIDATION_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,63})$";
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
}
