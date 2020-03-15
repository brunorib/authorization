package com.bribeiro.auth.rest.application.repository;

import com.bribeiro.auth.rest.application.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    private EntityManager em;

    public UserRepositoryImpl() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("com.bstore");
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

        return search.get(0);
    }

    @Override
    public User saveUser(User u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
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
        em.merge(u);
        em.getTransaction().commit();
        return u;
    }
}
