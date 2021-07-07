package com.shkubel.project.mapper;

import com.shkubel.project.dto.UserDTO;
import com.shkubel.project.models.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper (config = CommonMapperConfig.class,
        componentModel = "spring")
@Component
public interface UserMapper {

    UserDTO toDTO (User user);
}
