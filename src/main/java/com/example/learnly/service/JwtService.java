package com.example.learnly.service;

import com.example.learnly.dto.jwt.JwtResponseDto;
import com.example.learnly.entity.user.User;

public interface JwtService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    JwtResponseDto refreshTokens(String refreshToken, User user);

    boolean isValid(String token);

    boolean isAccessToken(String token);

    boolean isRefreshToken(String token);

    String getEmailFromToken(String token);

}
