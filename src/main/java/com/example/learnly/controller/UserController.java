package com.example.learnly.controller;

import com.example.learnly.dto.user.UpdateProfileDto;
import com.example.learnly.dto.user.UserResponseDto;
import com.example.learnly.entity.user.User;
import com.example.learnly.mapper.UserMapper;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        List<UserResponseDto> responses = userMapper.toResponses(users);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        UserResponseDto response = userMapper.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/update-profile")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileDto updateProfileDto) {
        User user = userService.updateProfile(id, updateProfileDto);
        UserResponseDto response = userMapper.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
