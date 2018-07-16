package com.user.data.validation.user;

import com.user.data.core.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserValidationTest {
    private User user;
    private Validator validator;

    @Before
    public void before() {
        user = new User();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test(expected = Test.None.class)
    public void fieldNameIsCorrect() throws NoSuchFieldException {
        user.getClass().getDeclaredField(UserValidation.USERNAME);
        user.getClass().getDeclaredField(UserValidation.PASSWORD);
        user.getClass().getDeclaredField(UserValidation.EMAIL);
        user.getClass().getDeclaredField(UserValidation.PHONE);

        assertThat(User.class.getDeclaredFields().length).isEqualTo(4);
    }

    private User getValidUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("P#assword8");
        user.setEmail("email@gmail.com");
        user.setPhone("34234235");
        return user;
    }

    @Test
    public void userMustBeValid() {
        user = getValidUser();
        assertThat(validator.validate(user).size() == 0).isTrue();
    }

    @Test
    public void usernameMustBeInvalid() {
        user = getValidUser();
        user.setUsername("user_name$");
        assertThat(validator.validate(user).size() == 1).isTrue();
    }

    @Test
    public void passwordMustBeInvalid() {
        user = getValidUser();
        user.setPassword("password");
        assertThat(validator.validate(user).size() == 1).isTrue();
    }

    @Test
    public void emailMustBeInvalid() {
        user = getValidUser();
        user.setEmail("email");
        assertThat(validator.validate(user).size() == 1).isTrue();
    }

    @Test
    public void phoneMustBeInvalid() {
        user = getValidUser();
        user.setPhone("+++45234");
        assertThat(validator.validate(user).size() == 1).isTrue();
    }

    @Test
    public void phoneMustBeValid() {
        user = getValidUser();
        user.setPhone("+452334224");
        assertThat(validator.validate(user).size() == 0).isTrue();
    }
}