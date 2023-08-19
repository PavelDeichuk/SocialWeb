package com.pavel.socialweb.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavel.socialweb.Entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Post")
public class PostDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "имя поста", example = "тест", accessMode = Schema.AccessMode.READ_ONLY)
    private String title;

    @Schema(description = "описание поста", example = "тест", accessMode = Schema.AccessMode.READ_ONLY)
    private String description;

    @JsonIgnore
    private UserEntity user;
}
