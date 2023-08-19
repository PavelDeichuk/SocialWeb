package com.pavel.socialweb.Mapper;

import com.pavel.socialweb.Dto.PostDto;
import com.pavel.socialweb.Entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostDto POST_DTO(PostEntity postEntity);
}
