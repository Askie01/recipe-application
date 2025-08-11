package com.askie01.recipeapplication.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class SuccessfulHttpResponse implements HttpResponse {
    private Integer code;
    private String message;
}
