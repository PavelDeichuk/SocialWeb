package com.pavel.socialweb.Model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {
    private String error;

    private String error_description;
}
