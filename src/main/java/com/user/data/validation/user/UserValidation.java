package com.user.data.validation.user;

import com.user.data.core.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidation implements ConstraintValidator<UserConstraint, User> {
    private String usernameRegExp;
    private String passwordRegExp;
    private String emailRegExp;
    private String phoneRegExp;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";


    @Override
    public void initialize(UserConstraint constraintAnnotation) {
        usernameRegExp = constraintAnnotation.usernameRegExp();
        passwordRegExp = constraintAnnotation.passwordRegExp();
        emailRegExp = constraintAnnotation.emailRegExp();
        phoneRegExp = constraintAnnotation.phoneRegExp();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        int validCount = 0;

        if (user == null) return true;
        if (usernameValidate(user, constraintValidatorContext)) validCount++;
        if (passwordValidate(user, constraintValidatorContext)) validCount++;
        if (emailValidate(user, constraintValidatorContext)) validCount++;
        if (phoneValidate(user, constraintValidatorContext)) validCount++;

        return validCount == 4;
    }

    private void saveConstraintViolation(String field, String msg, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(field)
                .addConstraintViolation();

    }

    private boolean phoneValidate(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getPhone() == null) {
            saveConstraintViolation(PHONE, "{validation.user.message.empty}", constraintValidatorContext);
            return false;
        }
        if (!user.getPhone().matches(phoneRegExp)) {
            saveConstraintViolation(PHONE, "{validation.user.message.phone.invalid}", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean emailValidate(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getEmail() == null) {
            saveConstraintViolation(EMAIL, "{validation.user.message.empty}", constraintValidatorContext);
            return false;
        }
        if (!user.getEmail().matches(emailRegExp)) {
            saveConstraintViolation(EMAIL, "{validation.user.message.email.invalid}", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean passwordValidate(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getPassword() == null) {
            saveConstraintViolation(PASSWORD, "{validation.user.message.empty}", constraintValidatorContext);
            return false;
        }
        if (!user.getPassword().matches(passwordRegExp)) {
            saveConstraintViolation(PASSWORD, "{validation.user.message.password.invalid}", constraintValidatorContext);
            return false;
        }
        return true;
    }

    private boolean usernameValidate(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getUsername() == null) {
            saveConstraintViolation(USERNAME, "{validation.user.message.empty}", constraintValidatorContext);
            return false;
        }
        if (!user.getUsername().matches(usernameRegExp)) {
            saveConstraintViolation(USERNAME, "{validation.user.message.username.invalid}", constraintValidatorContext);
            return false;
        }
        return true;
    }
}
