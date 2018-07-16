package com.user.data.validation.user;

import com.user.data.core.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class UserValidation implements ConstraintValidator<UserConstraint, User> {
    private String usernameRegExp;
    private String passwordRegExp;
    private String emailRegExp;
    private String phoneRegExp;
    private Map<String, String> invalidFieldAndMsg;

    public final String USERNAME = "username";
    public final String PASSWORD = "password";
    public final String EMAIL = "email";
    public final String PHONE = "phone";

    public UserValidation(String usernameRegExp, String passwordRegExp, String emailRegExp, String phoneRegExp, Map<String, String> invalidFieldAndMsg) {
        this.usernameRegExp = usernameRegExp;
        this.passwordRegExp = passwordRegExp;
        this.emailRegExp = emailRegExp;
        this.phoneRegExp = phoneRegExp;
        this.invalidFieldAndMsg = invalidFieldAndMsg;
    }

    public UserValidation() {
//        invalidFieldAndMsg = new HashMap<>();
    }

    @Override
    public void initialize(UserConstraint constraintAnnotation) {
        usernameRegExp = constraintAnnotation.usernameRegExp();
        passwordRegExp = constraintAnnotation.passwordRegExp();
        emailRegExp = constraintAnnotation.emailRegExp();
        phoneRegExp = constraintAnnotation.phoneRegExp();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return true;
        usernameValidate(user);
        passwordValidate(user);
        emailValidate(user);
        phoneValidate(user);
        saveConstraintViolation(constraintValidatorContext);

        return invalidFieldAndMsg.size() == 0;
    }

    private void saveConstraintViolation(ConstraintValidatorContext constraintValidatorContext) {
        for (String field : invalidFieldAndMsg.keySet()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(invalidFieldAndMsg.get(field))
                    .addPropertyNode(field)
                    .addConstraintViolation();
        }
    }

    private void phoneValidate(User user) {
        if (user.getPhone() == null) {
            invalidFieldAndMsg.put(PHONE, "{validation.user.message.empty}");
            return;
        }
        if (!user.getPhone().matches(phoneRegExp)) {
            invalidFieldAndMsg.put(PHONE, "{validation.user.message.phone.invalid}");
        }
    }

    private void emailValidate(User user) {
        if (user.getEmail() == null) {
            invalidFieldAndMsg.put(EMAIL, "{validation.user.message.empty}");
            return;
        }
        if (!user.getEmail().matches(emailRegExp)) {
            invalidFieldAndMsg.put(EMAIL, "{validation.user.message.email.invalid}");
        }
    }

    private void passwordValidate(User user) {
        if (user.getPassword() == null) {
            invalidFieldAndMsg.put(PASSWORD, "{validation.user.message.empty}");
            return;
        }
        if (!user.getPassword().matches(passwordRegExp)) {
            invalidFieldAndMsg.put(PASSWORD, "{validation.user.message.password.invalid}");
        }
    }

    private void usernameValidate(User user) {
        if (user.getUsername() == null) {
            invalidFieldAndMsg.put(USERNAME, "{validation.user.message.empty}");
            return;
        }
        if (!user.getUsername().matches(usernameRegExp)) {
            invalidFieldAndMsg.put(PASSWORD, "{validation.user.message.username.invalid}");
        }
    }
}
