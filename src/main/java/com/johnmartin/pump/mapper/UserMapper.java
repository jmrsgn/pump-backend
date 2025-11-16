package com.johnmartin.pump.mapper;

import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(),
                                userEntity.getFirstName(),
                                userEntity.getLastName(),
                                userEntity.getEmail(),
                                userEntity.getPhone(),
                                userEntity.getRole(),
                                userEntity.getProfileImageUrl());
    }
}
