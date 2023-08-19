package com.pavel.socialweb.Mapper;

import com.pavel.socialweb.Dto.ImageDto;
import com.pavel.socialweb.Entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {
    ImageDto IMAGE_DTO(ImageEntity imageEntity);

    ImageEntity IMAGE_ENTITY(ImageDto imageDto);
}
