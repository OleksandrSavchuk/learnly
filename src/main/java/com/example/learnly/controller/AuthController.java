package com.example.learnly.controller;

import com.example.learnly.dto.jwt.JwtRefreshRequestDto;
import com.example.learnly.dto.jwt.JwtResponseDto;
import com.example.learnly.dto.user.UserLoginRequestDto;
import com.example.learnly.dto.user.UserRegisterRequestDto;
import com.example.learnly.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        JwtResponseDto jwtResponseDto = authService.register(userRegisterRequestDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        JwtResponseDto jwtResponseDto = authService.login(userLoginRequestDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponseDto> refresh(@RequestBody JwtRefreshRequestDto jwtRefreshRequestDto) {
        String refreshToken = jwtRefreshRequestDto.refreshToken();
        JwtResponseDto jwtResponseDto = authService.refresh(refreshToken);
        return ResponseEntity.ok(jwtResponseDto);
    }

}
