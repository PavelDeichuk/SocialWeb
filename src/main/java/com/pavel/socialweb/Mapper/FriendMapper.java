package com.pavel.socialweb.Mapper;

import com.pavel.socialweb.Dto.FriendDto;
import com.pavel.socialweb.Entity.FriendEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);
    FriendDto FRIEND_DTO(FriendEntity friendEntity);
}
