package com.shkubel.project.mapper;

import com.shkubel.project.dto.ProfileUserDTO;
import com.shkubel.project.models.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper (config = CommonMapperConfig.class,
        componentModel = "spring")
@Component
public interface ProfileUserMapper {

    ProfileUserDTO toDTO (User user);

}
