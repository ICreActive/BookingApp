package com.shkubel.project.mapper;

import com.shkubel.project.dto.UserDTO;
import com.shkubel.project.models.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO (User user);

}
