package com.user.data.service;

import com.user.data.core.User;
import com.user.data.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserService() {
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        List<Integer> special = new ArrayList<>();
        List<Integer> symbols = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        special.add(33);
        special.add(35);
        special.add(37);
        special.add(38);

        for (int i = 65, j = 48; i <= 122; ++i) {
            if (j < 58) numbers.add(Integer.valueOf(j++));
            if (!(i > 90 && i < 97)) symbols.add(i);
        }

        int size = 8 + rnd.nextInt(8);
        for (int i = 0; i < size; ++i) {
            int ch;
            if (i % 3 == 0) {
                ch = special.get(rnd.nextInt(special.size()));
            } else if (i % 2 == 0) {
                ch = symbols.get(rnd.nextInt(symbols.size()));
            } else {
                ch = numbers.get(rnd.nextInt(numbers.size()));
            }
            password.append(Character.toChars(ch));
        }
        return password.toString();
    }

    public User login(String username, String password) {
        if (repository.isUsernameExist(username)) {
            User user = repository.findByUsername(username);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isValid(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(user).size() == 0;
    }

    public Map<String, String> invalidFieldAndMsg(User user) {
        Map<String, String> errors = new HashMap<>();

        if (!isValid(user)) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            for (ConstraintViolation<User> error : validator.validate(user)) {
                errors.put(error.getPropertyPath().toString(), error.getMessage());
            }
        }
        return errors;
    }

    public boolean create(User user) {
        if (isValid(user) && !repository.isUsernameExist(user.getUsername())) {
            return repository.save(user);
        }
        return false;
    }

    public boolean update(User user) {
        if (isValid(user) && repository.isUsernameExist(user.getUsername())) {
            return repository.update(user);
        }
        return false;
    }

}
