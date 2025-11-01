package com.johnmartin.pump.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.johnmartin.pump.entities.User;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public User findByEmail(String email) {
        return users.get(email);
    }

    public void save(User user) {
        users.put(user.getEmail(), user);
    }
}
