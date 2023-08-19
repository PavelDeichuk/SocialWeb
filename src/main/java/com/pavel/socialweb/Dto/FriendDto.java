package com.pavel.socialweb.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Enum.FriendStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Friend")
public class FriendDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Статус добавления в друзья", example = "status", accessMode = Schema.AccessMode.READ_ONLY)
    private FriendStatus status;

    @JsonIgnore
    private UserEntity firstUser;

    @JsonIgnore
    private UserEntity secondUser;
}
