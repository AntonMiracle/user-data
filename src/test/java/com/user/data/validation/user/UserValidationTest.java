package com.user.data.validation.user;

import com.user.data.core.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserValidationTest {
    private User user;
    private Map<String, String> invalidFieldAndMsg;
    private UserValidation validation;
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void before() {
        user = new User();
        invalidFieldAndMsg = new HashMap<>();
        validation = new UserValidation("[A-Za-z]+","[A-Za-z]+","[A-Za-z@]+","[0-9]+",invalidFieldAndMsg);

        constraintValidatorContext = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);
        when(builder.addPropertyNode(any(String.class))).thenReturn(nodeBuilder);
        when(nodeBuilder.addConstraintViolation()).thenReturn(constraintValidatorContext);

    }

    @Test(expected = Test.None.class)
    public void fieldNameIsCorrect() throws NoSuchFieldException {
        user.getClass().getDeclaredField(validation.USERNAME);
        user.getClass().getDeclaredField(validation.PASSWORD);
        user.getClass().getDeclaredField(validation.EMAIL);
        user.getClass().getDeclaredField(validation.PHONE);

        assertThat(User.class.getDeclaredFields()).isEqualTo(4);
    }

    @Test
    public void isValidReturnTrue() {
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        user.setPhone("34234235");

        System.out.println(invalidFieldAndMsg.size()==0);
        for(String key : invalidFieldAndMsg.keySet()){
            System.out.println(key + " : " + invalidFieldAndMsg.get(key) + System.lineSeparator());
        }

        assertThat(validation.isValid(user, constraintValidatorContext)).isTrue();

    }
}