package com.example.learnly.mapper;

import com.example.learnly.dto.user.UserRegisterRequestDto;
import com.example.learnly.dto.user.UserResponseDto;
import com.example.learnly.entity.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        RoleMapper.class
})
public interface UserMapper {

    UserRegisterRequestDto toUserRegisterRequest(User user);

    User toUser(UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto toResponse(User user);

    List<UserResponseDto> toResponses(List<User> user);

}
