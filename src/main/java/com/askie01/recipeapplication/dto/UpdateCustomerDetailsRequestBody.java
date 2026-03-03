package com.askie01.recipeapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UpdateCustomerDetailsRequestBody {

    @Size(min = 3, message = "First name must contain at least 3 characters.")
    @NotBlank(message = "First name cannot be blank/null")
    private String firstName;

    @Size(min = 3, message = "Last name must contain at least 3 characters.")
    @NotBlank(message = "Last name cannot be blank/null")
    private String lastName;

    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email cannot be blank/null")
    private String email;

    @Size(min = 9, message = "Mobile number must be at least 9 digits long.")
    @NotBlank(message = "Mobile number cannot be blank/null")
    private String mobileNumber;
}
