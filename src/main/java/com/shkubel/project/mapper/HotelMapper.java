package com.shkubel.project.mapper;

import com.shkubel.project.dto.HotelDTO;
import com.shkubel.project.models.entity.Room;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface HotelMapper {

    HotelDTO toDTO(Room room);

}
