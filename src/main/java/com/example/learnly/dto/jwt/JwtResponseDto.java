package com.example.learnly.dto.jwt;

public record JwtResponseDto(

        Long userId,

        String email,

        String accessToken,

        String refreshToken

) {
}
