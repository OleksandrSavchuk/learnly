package com.example.learnly.service;

import com.example.learnly.dto.jwt.JwtResponseDto;
import com.example.learnly.dto.user.UserLoginRequestDto;
import com.example.learnly.dto.user.UserRegisterRequestDto;

public interface AuthService {

    JwtResponseDto register(UserRegisterRequestDto userRegisterRequestDto);

    JwtResponseDto login(UserLoginRequestDto userLoginRequestDto);

    JwtResponseDto refresh(String refreshToken);

}
