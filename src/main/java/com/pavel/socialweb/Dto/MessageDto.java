package com.pavel.socialweb.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Message")
public class MessageDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "описание сообщения", example = "тест", accessMode = Schema.AccessMode.READ_ONLY)
    private String description;
}
