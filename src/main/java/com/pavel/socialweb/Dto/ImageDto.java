package com.pavel.socialweb.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Image")
public class ImageDto {
    @Schema(description = "идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "имя фотографии", example = "test", accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    @Schema(description = "путь к фотографии", example = "C://test.png", accessMode = Schema.AccessMode.READ_ONLY)
    private String path;

    @Schema(description = "размер фотографии", example = "150018", accessMode = Schema.AccessMode.READ_ONLY)
    private Long size;
}
