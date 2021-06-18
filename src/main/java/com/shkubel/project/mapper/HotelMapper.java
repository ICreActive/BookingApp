package com.shkubel.project.mapper;

import com.shkubel.project.dto.HotelDTO;
import com.shkubel.project.dto.UserDTO;
import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HotelMapper {
    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);
    HotelDTO toDTO (Hotel hotel);

}
