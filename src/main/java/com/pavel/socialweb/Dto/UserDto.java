package com.pavel.socialweb.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User")
public class UserDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "логин пользователя", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(description = "почта пользователя", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private String email;

    private LocalDateTime createAt;
}
