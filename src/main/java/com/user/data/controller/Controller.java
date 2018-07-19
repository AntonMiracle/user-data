package com.user.data.controller;

import com.user.data.core.User;
import com.user.data.repository.UserRepository;
import com.user.data.service.UserService;
import com.user.data.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.user.data.controller.Choice.*;

public class Controller {
    private View view;
    private Map<Integer, Choice> numberOfChoice;
    private Scanner scanner = new Scanner(System.in);
    private UserService service;
    private UserRepository repository;
    private User user;

    public Controller(View view) {
        this.view = view;
        this.repository = new UserRepository();
        this.service = new UserService(repository);
    }

    private void setNumberOfChoice(Map<Choice, Integer> menu) {
        this.numberOfChoice = new HashMap<>();
        for (Choice choice : menu.keySet()) {
            numberOfChoice.put(menu.get(choice), choice);
        }
    }

    public Choice getChoiceByNumber(int number) {
        return numberOfChoice.get(number);
    }

    public void loginPage() {
        user = null;
        String error = "";
        boolean isChoiceFinish = false;
        while (!isChoiceFinish) {
            setNumberOfChoice(view.showMenu("MENU", LOGIN, REGISTRATION, EXIT));
            if (error.length() > 0) {
                System.out.println(error);
                error = "";
            }
            String choice = scanner.nextLine();
            if (choice == null || !choice.trim().matches("^[1-9]$")) {
                error = "ERROR : choice not exist, try again";
            } else {
                switch (getChoiceByNumber(Integer.valueOf(choice))) {
                    case EXIT:
                        System.exit(0);
                        isChoiceFinish = true;
                        break;
                    case REGISTRATION:
                        registrationPage();
                        isChoiceFinish = true;
                        break;
                    case LOGIN:
                        login();
                        isChoiceFinish = true;
                        break;
                    default:
                        error = "ERROR : choice not exist, try again";
                }
            }
        }
    }

    public void registrationPage() {
        boolean isRegistrationFinish = false;
        String error = "";
        User user = new User();
        while (!isRegistrationFinish) {
            setNumberOfChoice(view.showMenu(REGISTRATION.toString(), USERNAME, PASSWORD, PHONE, EMAIL, REGISTRATION, EXIT));
            if (error.length() > 0) {
                System.out.println(error);
                error = "";
            }
            String choice = scanner.nextLine();
            if (choice == null || !choice.trim().matches("^[1-9]$")) {
                error = "ERROR : choice not exist, try again";
            } else {
                switch (getChoiceByNumber(Integer.valueOf(choice))) {
                    case USERNAME:
                        System.out.println("Enter username:");
                        user.setUsername(scanner.nextLine().trim());
                        break;
                    case PASSWORD:
                        while (true) {
                            System.out.println("Enter password:");
                            String password = scanner.nextLine().trim();
                            System.out.println("Confirm password:");
                            String confirm = scanner.nextLine().trim();
                            if (password.equals(confirm)) {
                                user.setPassword(password);
                                break;
                            } else {
                                System.out.println("confirm password not equal, try again");
                            }
                        }
                        break;
                    case PHONE:
                        System.out.println("Enter phone:");
                        user.setPhone(scanner.nextLine().trim());
                        break;
                    case EMAIL:
                        System.out.println("Enter email");
                        user.setEmail(scanner.nextLine().trim());
                        break;
                    case REGISTRATION:
                        if (service.isValid(user) && !repository.isUsernameExist(user.getUsername())) {
                            service.create(user);
                            loginPage();
                            isRegistrationFinish = true;
                        } else {
                            StringBuilder errors = new StringBuilder();
                            errors.append("ERROR while registration:" + System.lineSeparator());
                            Map<String, String> fieldAndMsg = service.invalidFieldAndMsg(user);
                            if (user.getUsername() != null && user.getUsername().length() > 0 && repository.isUsernameExist(user.getUsername())) {
                                errors.append(USERNAME.toString() + " already exist" + System.lineSeparator());
                            }
                            for (String key : fieldAndMsg.keySet()) {
                                errors.append(key + " : " + fieldAndMsg.get(key) + System.lineSeparator());
                            }
                            error = errors.toString();
                        }
                        break;
                    case EXIT:
                        loginPage();
                        isRegistrationFinish = true;
                        break;
                    default:
                        error = "ERROR : choice not exist, try again";
                }
            }
        }

    }

    public void login() {
        String error = "";
        boolean isLoginSuccess = false;
        while (!isLoginSuccess) {
            System.out.println("ENTER LOGIN:");
            String username = scanner.nextLine();
            System.out.println("ENTER PASSWORD:");
            String password = scanner.nextLine();
            user = service.login(username, password);
            if (user != null) {
                homePage();
                isLoginSuccess = true;
            } else {
                error = "username or password wrong, try again";
                boolean isChoiceFinish = false;
                while (!isChoiceFinish) {
                    setNumberOfChoice(view.showMenu("LOGIN", EXIT, LOGIN));
                    if (error.length() > 0) {
                        System.out.println(error);
                        error = "";
                    }
                    String choice = scanner.nextLine();
                    if (choice == null || !choice.trim().matches("^[1-9]$")) {
                        error = "ERROR : choice not exist, try again";
                    } else {
                        switch (getChoiceByNumber(Integer.valueOf(choice))) {
                            case EXIT:
                                loginPage();
                                isChoiceFinish = true;
                                break;
                            case LOGIN:
                                login();
                                isChoiceFinish = true;
                                break;
                            default:
                                error = "ERROR : choice not exist, try again";
                        }
                    }
                }
            }

        }
    }

    private void homePage() {
        String error = "";
        boolean isExit = false;
        while (!isExit) {
            setNumberOfChoice(view.showMenu("HOME PAGE", EMAIL, PHONE, EXIT));
            System.out.println(user);
            if (error.length() > 0) {
                System.out.println(error);
                error = "";
            }
            String choice = scanner.nextLine();
            if (choice == null || !choice.trim().matches("^[1-9]$")) {
                error = "ERROR : choice not exist, try again";
            } else {
                switch (getChoiceByNumber(Integer.valueOf(choice))) {
                    case EXIT:
                        user = null;
                        loginPage();
                        isExit = true;
                        break;
                    case PHONE:
                        boolean isEditFinish = false;
                        while (!isEditFinish) {
                            setNumberOfChoice(view.showMenu("PHONE", EDIT, EXIT));
                            if (error.length() > 0) {
                                System.out.println(error);
                                error = "";
                            }
                            choice = scanner.nextLine();
                            if (choice == null || !choice.trim().matches("^[1-9]$")) {
                                error = "ERROR : choice not exist, try again";
                            } else {
                                switch (getChoiceByNumber(Integer.valueOf(choice))) {
                                    case EXIT:
                                        homePage();
                                        isEditFinish = true;
                                        break;
                                    case EDIT:
                                        User edit = new User();
                                        edit.setUsername(user.getUsername());
                                        edit.setPassword(user.getPassword());
                                        edit.setPhone(user.getPhone());
                                        edit.setEmail(user.getEmail());
                                        System.out.println("ENTER new phone");
                                        edit.setPhone(scanner.nextLine().trim());
                                        if (service.isValid(edit)) {
                                            user.setPhone(edit.getPhone());
                                            service.update(user);
                                            homePage();
                                            isEditFinish = true;
                                        } else {
                                            StringBuilder errors = new StringBuilder();
                                            Map<String, String> fieldAndMsg = service.invalidFieldAndMsg(user);
                                            for (String key : fieldAndMsg.keySet()) {
                                                errors.append(key + " : " + fieldAndMsg.get(key) + System.lineSeparator());
                                            }
                                            error = errors.toString();
                                        }
                                        break;
                                    default:
                                        error = "ERROR : choice not exist, try again";
                                }
                            }
                        }
                        break;
                    case EMAIL:
                        boolean isEmailEditFinish = false;
                        while (!isEmailEditFinish) {
                            setNumberOfChoice(view.showMenu("EMAIL", EDIT, EXIT));
                            if (error.length() > 0) {
                                System.out.println(error);
                                error = "";
                            }
                            choice = scanner.nextLine();
                            if (choice == null || !choice.trim().matches("^[1-9]$")) {
                                error = "ERROR : choice not exist, try again";
                            } else {
                                switch (getChoiceByNumber(Integer.valueOf(choice))) {
                                    case EXIT:
                                        homePage();
                                        isEmailEditFinish = true;
                                        break;
                                    case EDIT:
                                        User edit = new User();
                                        edit.setUsername(user.getUsername());
                                        edit.setPassword(user.getPassword());
                                        edit.setPhone(user.getPhone());
                                        edit.setEmail(user.getEmail());
                                        System.out.println("ENTER new email");
                                        edit.setEmail(scanner.nextLine().trim());
                                        if (service.isValid(edit)) {
                                            user.setEmail(edit.getEmail());
                                            service.update(user);
                                            homePage();
                                            isEmailEditFinish = true;
                                        } else {
                                            StringBuilder errors = new StringBuilder();
                                            Map<String, String> fieldAndMsg = service.invalidFieldAndMsg(user);
                                            for (String key : fieldAndMsg.keySet()) {
                                                errors.append(key + " : " + fieldAndMsg.get(key) + System.lineSeparator());
                                            }
                                            error = errors.toString();
                                        }
                                        break;
                                    default:
                                        error = "ERROR : choice not exist, try again";
                                }
                            }
                        }
                        break;

                }

            }
        }
    }
}