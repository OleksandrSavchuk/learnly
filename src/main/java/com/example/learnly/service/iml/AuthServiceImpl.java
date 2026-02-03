package com.example.learnly.service.iml;

import com.example.learnly.dto.jwt.JwtResponseDto;
import com.example.learnly.dto.user.CreateUserDto;
import com.example.learnly.dto.user.UserLoginRequestDto;
import com.example.learnly.dto.user.UserRegisterRequestDto;
import com.example.learnly.entity.user.User;
import com.example.learnly.exception.InvalidApiRequestException;
import com.example.learnly.exception.InvalidTokenException;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.service.AuthService;
import com.example.learnly.service.JwtService;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtService jwtService;

    @Override
    public JwtResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {
        Objects.requireNonNull(userRegisterRequestDto, "userRegisterRequestDto");

        if (!userRegisterRequestDto.password().equals(userRegisterRequestDto.passwordConfirmation())) {
            throw new InvalidApiRequestException("Password and confirmation do not match");
        }

        CreateUserDto createUserDto = new CreateUserDto(
                userRegisterRequestDto.email(),
                userRegisterRequestDto.password()
        );

        Long userId = userService.createUser(createUserDto);

        User user = userService.getById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user immediately after creating. User id: " + userId));

        return createJwtResponse(user);
    }

    @Override
    public JwtResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        Objects.requireNonNull(userLoginRequestDto, "userLoginRequestDto");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.email(), userLoginRequestDto.password());

        authenticationManager.authenticate(authentication);

        User user = userService.getByEmail(userLoginRequestDto.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found after success authorization. User email: " + userLoginRequestDto.email()));

        return createJwtResponse(user);
    }

    @Override
    public JwtResponseDto refresh(String refreshToken) {
        Objects.requireNonNull(refreshToken, "refreshToken");

        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Invalid token type. Refresh token required.");
        }

        if (!jwtService.isValid(refreshToken)) {
            throw new InvalidTokenException("Refresh token is invalid");
        }

        String email = jwtService.getEmailFromToken(refreshToken);
        User user = userService
                .getByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with email: "));

        return jwtService.refreshTokens(refreshToken, user);
    }

    private JwtResponseDto createJwtResponse(User user) {
        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);

        return new JwtResponseDto(
                user.getId(),
                user.getEmail(),
                accessToken,
                refreshToken
        );
    }

}
