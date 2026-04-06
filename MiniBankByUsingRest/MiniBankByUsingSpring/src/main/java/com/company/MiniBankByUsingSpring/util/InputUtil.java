package com.company.MiniBankByUsingSpring.util;

import com.company.MiniBankByUsingSpring.exception.InputValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class InputUtil {

    private static final Logger logger = LoggerFactory.getLogger(InputUtil.class);

    public static String requireText(String question) {
        System.out.println(question);
        return new Scanner(System.in).nextLine();
    }

    public static double requireDouble(String question) {
        System.out.println(question);
        return new Scanner(System.in).nextDouble();
    }

    public static int requireNumber(String question) {
        System.out.println(question);
        return new Scanner(System.in).nextInt();
    }

    public static String requireUsername() {
        while (true) {
            try {
                String username = requireText("Enter username: ");
                ValidationUtil.validateUsername(username);
                return username;
            } catch (InputValidationException ex) {
                logger.warn(ex.getMessage());
            } catch (Exception e) {
                System.err.println("Please, inpout a valid username or password!");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public static String requirePassword() {
        while (true) {
            try {
                String password = requireText(("Enter password: "));
                ValidationUtil.validatePasswordStrength(password);
                return password;
            } catch (InputValidationException ex) {
                logger.warn(ex.getMessage());
            } catch (Exception e) {
                System.err.println("Please, inpout a valid username or password!");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public static String requireName() {
        while (true) {
            try {
                String name = requireText(("Enter name: "));
                ValidationUtil.validateName(name);
                return name;
            } catch (InputValidationException ex) {
                logger.warn(ex.getMessage());
            } catch (Exception e) {
                System.err.println("Please, inpout a valid name!");
                new Scanner(System.in).nextLine();
            }
        }
    }

    public static String requireSurname() {
        while (true) {
            try {
                String surname = requireText(("Enter surname: "));
                ValidationUtil.validateSurname(surname);
                return surname;
            } catch (InputValidationException ex) {
                logger.warn(ex.getMessage());
            } catch (Exception e) {
                System.err.println("Please, inpout a valid surname!");
                new Scanner(System.in).nextLine();
            }
        }

    }

    public static String requireAzeID() {
        while (true) {
            try {
                String azeId = requireText(("Enter AZE ID: ")).toUpperCase().trim();
                ValidationUtil.validateAzeID(azeId);
                return azeId;
            } catch (InputValidationException e) {
                logger.warn(e.getMessage());
            } catch (Exception ex) {
                System.err.println("Please enter a valid ID!");
                new Scanner(System.in).nextLine();

            }
        }
    }

    public static int requireAge() {
        while (true) {
            try {
                int age = requireNumber("Enter Age: ");
                ValidationUtil.validateAge(age);
                return age;
            } catch (InputValidationException e) {
                logger.warn(e.getMessage());

            } catch (Exception e) {
                System.err.println("Please enter a valid number!");
                new Scanner(System.in).nextLine();
            }
        }
    }
}
