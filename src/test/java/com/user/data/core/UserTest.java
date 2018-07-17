package com.user.data.core;

import com.user.data.validation.user.UserConstraint;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetUsername() {
        String username = "username";
        user.setUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    public void setAndGetPassword() {
        String password = "password";
        user.setPassword(password);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    public void setAndGetEmail() {
        String email = "email@gmail.com";
        user.setEmail(email);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    public void setAndGetPhone() {
        String phone = "+432034523";
        user.setPhone(phone);
        assertThat(user.getPhone()).isEqualTo(phone);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(user.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(user.toString().startsWith(user.getClass().getSimpleName() + "{")).isTrue();
        assertThat(user.toString().endsWith("}")).isTrue();
    }

    @Test
    public void shouldHasUserConstraintAnnotation() {
        Class userClass = User.class;
        boolean isContains = false;

        for (Annotation annotation : userClass.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(UserConstraint.class)) isContains = true;
        }

        assertThat(isContains).isTrue();
    }

    @Test
    public void setAndGetId() {
        user.setId(1L);
        assertThat(user.getId()).isEqualTo(1L);
    }
}