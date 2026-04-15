package com.askie01.recipeapplication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CustomerProfileResponseBody {
    private String username;
    private String firstName;
    private String lastName;
}
