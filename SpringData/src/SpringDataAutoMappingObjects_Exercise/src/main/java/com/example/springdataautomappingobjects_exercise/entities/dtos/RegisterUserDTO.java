package com.example.springdataautomappingobjects_exercise.entities.dtos;

import com.example.springdataautomappingobjects_exercise.entities.User;
import org.modelmapper.ModelMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.springdataautomappingobjects_exercise.constants.Messages.*;
import static com.example.springdataautomappingobjects_exercise.constants.RegExpValidations.*;

public class RegisterUserDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public RegisterUserDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setFullName(fullName);
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.find()) {
            throw new IllegalArgumentException(INCORRECT_EMAIL_MESSAGE);
        }

        this.email = email;
    }

    public void setPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_VALIDATION_REGEX);

        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {
            throw new IllegalArgumentException(INCORRECT_PASSWORD_MESSAGE);
        }

        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!getPassword().equals(confirmPassword)) {
            throw new IllegalArgumentException(MISMATCHING_PASSWORDS_MESSAGE);
        }

        this.confirmPassword = confirmPassword;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public User mapToUser() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, User.class);
    }
}
