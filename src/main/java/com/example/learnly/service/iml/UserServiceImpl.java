package com.example.learnly.service.iml;

import com.example.learnly.dto.user.CreateUserDto;
import com.example.learnly.dto.user.UpdateProfileDto;
import com.example.learnly.entity.user.Role;
import com.example.learnly.entity.user.User;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.repository.RoleRepository;
import com.example.learnly.repository.UserRepository;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email");

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with email: " + email));
    }

    @Transactional
    @Override
    public Long createUser(CreateUserDto createUserDto) {
        Objects.requireNonNull(createUserDto, "createUserDto");

        if (!Set.of("STUDENT", "INSTRUCTOR").contains(createUserDto.role().trim().toUpperCase())) {
            throw new IllegalArgumentException("Role is not allowed");
        }

        String encoded = passwordEncoder.encode(createUserDto.password());

        Role role = roleRepository
                .findByName(createUserDto.role().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Not found default role for new user " + createUserDto.email()));

        User user = User.builder()
                .email(createUserDto.email())
                .password(encoded)
                .role(role)
                .firstName(createUserDto.firstName())
                .lastName(createUserDto.lastName())
                .build();

        user = userRepository.save(user);

        return user.getId();
    }

    @Override
    public User updateProfile(Long id, UpdateProfileDto updateProfileDto) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));

        String firstName = updateProfileDto.firstName();
        if (firstName != null) {
            user.setFirstName(firstName);
        }

        String lastName = updateProfileDto.lastName();
        if (lastName != null) {
            user.setLastName(lastName);
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
