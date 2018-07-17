package com.user.data.repository;

import com.user.data.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryTest {
    private UserRepository repository;
    private String username1 = "u11";
    private String username2 = "u22";

    @Before
    public void before() {
        repository = new UserRepository();
    }

    @After
    public void after() {
        if (repository.isUsernameExist(username1)) {
            repository.delete(repository.findByUsername(username1).getId());
        }
        if (repository.isUsernameExist(username2)) {
            repository.delete(repository.findByUsername(username2).getId());
        }
    }

    @Test
    public void shouldSaveUser() {
        int size = repository.findAll().size();

        User user = new User();
        user.setUsername(username1);
        user.setPassword("P#assword2");
        user.setEmail("email@gmail.com");
        user.setPhone("+23234235");

        repository.save(user);

        assertThat(user.getId() != 0).isTrue();
        assertThat(repository.findAll().size()).isEqualTo(size + 1);
    }

    @Test
    public void shouldFindUserById() {
        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword("P#assword2");
        exist.setEmail("email@gmail.com");
        exist.setPhone("+23234235");
        repository.save(exist);

        User current = repository.findById(exist.getId());
        assertThat(current != null).isTrue();
        assertThat(current.getUsername()).isEqualTo(username1);
    }

    @Test
    public void shouldFindAllUsers() {
        int size = repository.findAll().size();
        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword("P#assword2");
        exist.setEmail("email@gmail.com");
        exist.setPhone("+23234235");
        repository.save(exist);

        List<User> users = repository.findAll();
        assertThat(users.contains(exist)).isTrue();
        assertThat(users.size()).isEqualTo(size + 1);
    }

    @Test
    public void shouldFindByUsername() {
        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword("P#assword2");
        exist.setEmail("email@gmail.com");
        exist.setPhone("+23234235");
        repository.save(exist);

        User current = repository.findByUsername(exist.getUsername());
        assertThat(current != null).isTrue();
        assertThat(current.getUsername()).isEqualTo(username1);
    }

    @Test
    public void usernameMustExist() {
        assertThat(repository.isUsernameExist(username1)).isFalse();

        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword("P#assword2");
        exist.setEmail("email@gmail.com");
        exist.setPhone("+23234235");
        repository.save(exist);

        assertThat(repository.isUsernameExist(username1)).isTrue();
    }

    @Test
    public void shouldDeleteUserById() {
        int size = repository.findAll().size();

        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword("P#assword2");
        exist.setEmail("email@gmail.com");
        exist.setPhone("+23234235");
        repository.save(exist);

        repository.delete(exist.getId());
        assertThat(repository.findAll().size() == size).isTrue();
    }

    @Test
    public void shouldUpdateUser() {
        String email = "email@gmail.com";
        String password = "P#assword2";
        String phone = "2312435134";

        User exist = new User();
        exist.setUsername(username1);
        exist.setPassword(password);
        exist.setEmail(email);
        exist.setPhone(phone);
        repository.save(exist);

        exist.setUsername(username2);
        exist.setPassword(password + "p");
        exist.setEmail(email + "e");
        exist.setPhone(phone + "23");
        repository.update(exist);

        User current = repository.findById(exist.getId());

        assertThat(repository.isUsernameExist(username1)).isFalse();
        assertThat(repository.isUsernameExist(username2)).isTrue();
        assertThat(current.getId()).isEqualTo(exist.getId());
        assertThat(current.getUsername()).isEqualTo(username2);
        assertThat(current.getPassword()).isNotEqualTo(password);
        assertThat(current.getEmail()).isNotEqualTo(email);
        assertThat(current.getPhone()).isNotEqualTo(phone);
    }

}