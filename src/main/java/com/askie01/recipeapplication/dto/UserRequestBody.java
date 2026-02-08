package com.askie01.recipeapplication.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "image")
@EqualsAndHashCode
public class UserRequestBody {

    @NotBlank(message = "Username cannot be blank/null")
    private String username;

    @NotBlank(message = "Password cannot be blank/null")
    private String password;

    @NotBlank(message = "First name cannot be blank/null")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank/null")
    private String lastName;

    @Max(value = 5 * 1024 * 1024, message = "Image cannot be larger than 5 MBs")
    private byte[] image;

    @NotBlank(message = "Email cannot be blank/null")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank/null")
    private String mobileNumber;
}
