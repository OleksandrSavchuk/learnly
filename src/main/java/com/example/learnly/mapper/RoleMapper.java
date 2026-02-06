package com.example.learnly.mapper;

import com.example.learnly.dto.role.RoleResponseDto;
import com.example.learnly.entity.user.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDto toResponse(Role role);

}
