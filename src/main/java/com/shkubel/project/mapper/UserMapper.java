package com.shkubel.project.mapper;

import com.shkubel.project.dto.UserDto;
import com.shkubel.project.models.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper (config = CommonMapperConfig.class,
        componentModel = "spring")

@Component
public interface UserMapper {

    UserDto toDTO (User user);
}
