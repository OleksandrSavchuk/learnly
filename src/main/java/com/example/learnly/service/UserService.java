package com.example.learnly.service;

import com.example.learnly.dto.user.CreateUserDto;
import com.example.learnly.dto.user.UpdateProfileDto;
import com.example.learnly.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);

    Long createUser(CreateUserDto createUserDto);

    User updateProfile(Long id, UpdateProfileDto updateProfileDto);

    void deleteById(Long id);

    boolean existsByEmail(String email);

}
