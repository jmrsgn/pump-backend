package com.johnmartin.pump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnmartin.pump.entities.UserEntity;
import com.johnmartin.pump.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
