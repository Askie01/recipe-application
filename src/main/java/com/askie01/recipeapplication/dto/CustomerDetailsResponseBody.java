package com.askie01.recipeapplication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CustomerDetailsResponseBody {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
