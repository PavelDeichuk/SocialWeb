package com.pavel.socialweb.Dto;

import com.pavel.socialweb.Entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Subscription")
public class SubscriptionDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private UserEntity sub_user;

    private UserEntity pub_user;

    private LocalDateTime createAt;
}
