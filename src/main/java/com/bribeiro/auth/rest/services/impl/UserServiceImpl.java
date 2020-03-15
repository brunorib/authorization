package com.bribeiro.auth.rest.services.impl;

import com.bribeiro.auth.rest.application.model.User;
import com.bribeiro.auth.rest.application.repository.UserRepository;
import com.bribeiro.auth.rest.application.repository.UserRepositoryImpl;
import com.bribeiro.auth.rest.utils.HashUtil;
import com.bribeiro.auth.rest.services.UserService;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public class UserServiceImpl implements UserService {

    // For 16 chars
    private static int SALT_LEN = 8;

    private SecureRandom random = new SecureRandom();

    private UserRepository repository = new UserRepositoryImpl();

    @Override
    public User getUser(int id) {
        return repository.getUserById(id);
    }

    @Override
    public User createUser(User u) {
        byte[] saltAsBytes = new byte[SALT_LEN];
        random.nextBytes(saltAsBytes);
        char[] salt = Hex.encodeHex(saltAsBytes);
        u.setPassword(HashUtil.hashPassword(u.getPassword(), salt));
        u.setSalt(salt);
        return repository.saveUser(u);
    }

    @Override
    public User updateUser(User u) {
        return repository.updateUser(u);
    }

    @Override
    public boolean findById(int userId) {
        return repository.getUserById(userId) != null;
    }

    @Override
    public User findByUsername(String username) {
        return repository.getUserByUsername(username);
    }
}
