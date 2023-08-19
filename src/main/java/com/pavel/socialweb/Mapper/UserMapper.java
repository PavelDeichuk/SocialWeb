package com.pavel.socialweb.Mapper;


import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto USER_DTO(UserEntity userEntity);
}
