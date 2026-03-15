package com.campusconnect.authservice.mapper;


import com.campusconnect.authservice.model.dto.UserDTO;
import com.campusconnect.authservice.model.entity.Role;
import com.campusconnect.authservice.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                user.getRoles() != null ? valueOf(user.getRoles()) : null
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .enabled(userDTO.getEnabled())
                .roles(userDTO.getRoles() != null ? Role.valueOf(userDTO.getRoles().toUpperCase()) : null)
                .build();
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        return userDTOs.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
