package com.bribeiro.auth.rest.application.repository;

import com.bribeiro.auth.config.SqlConfig;
import com.bribeiro.auth.rest.application.db.model.User;

import javax.persistence.*;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    private EntityManager em;

    public UserRepositoryImpl() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("com.bribeiro.auth", SqlConfig.getInstance().getConfigMap());
        em = emf.createEntityManager();
    }

    @Override
    public User getUserById(int id) {
        User u = em.find(User.class, id);
        if (u == null) {
            return null;
        }
        em.detach(u);
        return u;
    }

    @Override
    public User getUserByUsername(String username) {
        return getUserBy("getByUsername", "username", username);
    }

    @Override
    public User getUserByEmail(String email) {
        return getUserBy("getByEmail", "email", email);
    }

    @Override
    public User saveUser(User u) {
        em.getTransaction().begin();
        em.persist(u);
        em.flush();
        em.getTransaction().commit();

        return u;
    }

    @Override
    public void deleteUser(String id) {
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        em.remove(u);
        em.flush();
        em.getTransaction().commit();
    }

    @Override
    public User updateUser(User u) {
        em.getTransaction().begin();
        User persisted = em.find(User.class, u.getId());
        if(u.getUsername() != null) {
            persisted.setUsername(u.getUsername());
        }
        if(u.getEmail() != null) {
            persisted.setEmail(u.getEmail());
        }
        if(u.getPassword() != null) {
            persisted.setPassword(u.getPassword());
            persisted.setSalt(u.getSalt());
        }
        em.flush();
        em.getTransaction().commit();
        return persisted;
    }

    private User getUserBy(String namedQuery, String field, String value) {
        List<User> search = em.createNamedQuery(namedQuery, User.class)
                .setParameter(field, value).getResultList();

        if (search.size() == 0) {
            return null;
        }

        User u = search.get(0);
        em.detach(u);

        return u;
    }
}
