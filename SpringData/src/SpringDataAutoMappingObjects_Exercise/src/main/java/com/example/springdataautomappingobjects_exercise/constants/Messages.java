package com.example.springdataautomappingobjects_exercise.constants;

public enum Messages {
    ;

    //Register user messages
    public static final String INCORRECT_EMAIL_MESSAGE = "Incorrect email";
    public static final String INCORRECT_PASSWORD_MESSAGE = "Incorrect password";
    public static final String MISMATCHING_PASSWORDS_MESSAGE = "The passwords are mismatching";
    public static final String SUCCESSFULLY_REGISTERED_USER = "%s was registered%n";
    public static final String USER_ALREADY_EXISTS = "User already exists";

    //Login user messages
    public static final String INCORRECT_USERNAME_OR_PASSWORD_MESSAGE = "Incorrect username / password";
    public static final String SUCCESSFULLY_LOGGED_IN_USER_MESSAGE = "Successfully logged in %s%n";

    //Logout user messages
    public static final String CANNOT_LOGOUT_USER_MESSAGE = "Cannot log out. No user was logged in.";
    public static final String SUCCESSFULLY_LOGOUT_USER_MESSAGE = "User %s successfully logged out%n";

    //Add game messages
    public static final String SUCCESSFULLY_ADDED_GAME = "Added %s%n";
    public static final String TITLE_UPPERCASE_LETTER_MESSAGE = "The title must start with an uppercase letter";
    public static final String TITLE_LENGTH_MESSAGE = "The title length must be between 3 and 100";
    public static final String PRICE_NOT_POSITIVE_MESSAGE = "The price must be a positive number";
    public static final String TRAILER_NULL_MESSAGE = "Trailer cannot be null";
    public static final String SIZE_NOT_POSITIVE_MESSAGE = "The size must be a positive number";
    public static final String INVALID_THUMBNAIL_URL_MESSAGE = "Invalid thumbnail URL";
    public static final String INVALID_DATE_MESSAGE = "Date cannot be null";
    public static final String DESCRIPTION_INVALID_LENGTH_MESSAGE = "The description length must be higher or equal to 20";
    public static final String USER_NOT_LOGGED_IN_MESSAGE = "User is not logged in";
    public static final String USER_NOT_ADMIN_MESSAGE = "The user must be an admin to add/edit games";
    public static final String DATE_NOT_GIVEN_MESSAGE = "Please insert a release date";

    //Edit game messages
    public static final String SUCCESSFULLY_EDITED_GAME = "Edited %s%n";
    public static final String INVALID_GAME_ID_MESSAGE = "There is no game with the given id";

}
