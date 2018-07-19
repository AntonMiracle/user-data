package com.user.data.service;

import com.user.data.core.User;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class UserServiceTest {
    @Test
    public void test() {
        User user = new User();
        user.setPassword("sd");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        for(ConstraintViolation<User> error : errors){
            System.out.println( error);
            System.out.println( error.getMessage());
            System.out.println( error.getPropertyPath().toString());
        }

        UserService service = new UserService();
        for(int i = 0; i < 10; ++i){
            System.out.println(service.generatePassword());
        }
    }
}