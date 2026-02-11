package com.example.learnly.dto.user;

import com.example.learnly.dto.role.RoleResponseDto;

public record UserResponseDto(

        Long id,

        String email,

        String firstName,

        String lastName,

        RoleResponseDto role

) {
}
