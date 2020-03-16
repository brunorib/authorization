package com.bribeiro.auth.rest.application.repository;

import com.bribeiro.auth.rest.application.exceptions.ServerException;
import com.bribeiro.auth.rest.application.exceptions.UserAlreadyExistsException;
import com.bribeiro.auth.rest.application.model.User;

import javax.persistence.*;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    private EntityManager em;

    public UserRepositoryImpl() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("com.bribeiro.auth");
        em = emf.createEntityManager();
    }

    @Override
    public User getUserById(int id) {
        User u = em.find(User.class, id);
        em.detach(u);
        return u;
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> search = em.createNamedQuery("getByUsername", User.class)
            .setParameter("username", username).getResultList();

        if (search.size() == 0) {
            return null;
        }

        User u = search.get(0);
        em.detach(u);

        return u;
    }

    @Override
    public User saveUser(User u) throws UserAlreadyExistsException, ServerException {
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } catch(RollbackException e) {
            //log
            if(e.getCause() instanceof PersistenceException) {
                throw new UserAlreadyExistsException();
            }

            throw new ServerException();
        }

        return u;
    }

    @Override
    public void deleteUser(String id) {
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        em.remove(u);
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

        em.getTransaction().commit();
        em.flush();
        return persisted;
    }
}