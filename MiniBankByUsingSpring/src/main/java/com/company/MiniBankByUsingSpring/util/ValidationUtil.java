package com.company.MiniBankByUsingSpring.util;

import com.company.MiniBankByUsingSpring.exception.InputValidationException;

public class ValidationUtil {

    public static void validateAge(int age) {
        if (age < 0) {
            throw new InputValidationException("Age cannot be negative!");
        }
        if (age < 18) {
            throw new InputValidationException("It is impossible to open an account for minor under the age of 18!");
        }
    }

    public static void validateUsername(String username) {
        if (username == null || username.isBlank() || username.length() < 6) {
            throw new InputValidationException("Your username cannot be less than 6 letters");
        }

        if (!username.matches("^(?!.*\\.{2})[a-zA-Z0-9._]{1,30}$")) {
            throw new InputValidationException("Please, wouldn't write (..) or more than 30 characters in username");
        }
    }

    public static void validatePasswordStrength(String password) {
        if (password == null || password.isBlank() || password.length() < 8) {
            throw new InputValidationException("Your password cannot be less than 8 letters");
        }
        if (!password.matches("^[a-zA-Z0-9.]{8,65}$")) {
            throw new InputValidationException("Password contains invalid characters or is too long!");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank() || name.length() < 3) {
            throw new InputValidationException("Your name cannot be less than 3 letters");
        }
        if (!name.matches("[a-zA-Z]{3,20}+")) {
            throw new InputValidationException("Only letters in name!");
        }
    }

    public static void validateSurname(String surname) {
        if (surname == null || surname.isBlank() || surname.length() < 4) {
            throw new InputValidationException("Your surname cannot be less than 3 letters");
        }
        if (!surname.matches("[a-zA-Z]{3,}+")) {
            throw new InputValidationException("Only letters in surname!");
        }
    }

    public static void validateAzeID(String azeId) {
        if (!azeId.matches("(?i)^(AA\\d{8}|AZE\\d{8})$")) {
            throw new InputValidationException("incorrectly AzeID!");
        }
    }
}
