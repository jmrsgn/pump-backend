package com.johnmartin.pump.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.johnmartin.pump.model.User;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }
}
