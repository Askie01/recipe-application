package com.askie01.recipeapplication.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class ErrorHttpResponse implements HttpResponse {
    private String path;
    private Integer code;
    private String message;
    private LocalDateTime timestamp;
}
