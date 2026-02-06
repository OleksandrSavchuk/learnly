package com.example.learnly.dto.user;

import com.example.learnly.dto.role.RoleResponseDto;

public record UserResponseDto(

        String email,

        String firstName,

        String lastName,

        RoleResponseDto role

) {
}
