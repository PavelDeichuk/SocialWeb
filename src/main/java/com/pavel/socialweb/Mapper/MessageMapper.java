package com.pavel.socialweb.Mapper;

import com.pavel.socialweb.Dto.MessageDto;
import com.pavel.socialweb.Entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    MessageDto MESSAGE_DTO(MessageEntity messageEntity);
}
