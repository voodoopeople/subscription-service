package org.example.dto.mapper;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    User toEntity(UserDto dto);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    UserDto toDto(User entity);
}
