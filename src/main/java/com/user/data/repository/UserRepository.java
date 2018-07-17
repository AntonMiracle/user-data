package com.user.data.repository;

import com.user.data.core.User;

import javax.persistence.*;
import java.util.List;

public class UserRepository {
    private static final EntityManager em = Persistence.createEntityManagerFactory("datasource-unit").createEntityManager();


    public boolean save(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    public boolean delete(long id) {
        if (findById(id) == null) return true;

        try {
            em.getTransaction().begin();
            em.remove(findById(id));
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }

    public boolean update(User user) {
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public boolean isUsernameExist(String username) {
        try {
            em.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'").getSingleResult();
        } catch (NoResultException ex) {
            return false;
        }
        return true;
    }

    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'", User.class);
        return query.getSingleResult();
    }

}
