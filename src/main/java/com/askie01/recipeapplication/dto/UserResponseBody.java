package com.askie01.recipeapplication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "image")
@EqualsAndHashCode
public class UserResponseBody {
    private String firstName;
    private String lastName;
    private byte[] image;
    private String email;
    private String mobileNumber;
}
