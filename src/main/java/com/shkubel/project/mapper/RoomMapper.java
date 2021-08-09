package com.shkubel.project.mapper;

import com.shkubel.project.dto.RoomDto;
import com.shkubel.project.models.entity.Room;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface RoomMapper {

    RoomDto toDTO(Room room);

}
