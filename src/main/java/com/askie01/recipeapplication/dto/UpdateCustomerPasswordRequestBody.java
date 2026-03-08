package com.askie01.recipeapplication.dto;

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
public class UpdateCustomerPasswordRequestBody {

    @Size(min = 3, message = "Old password must contain at least 3 characters.")
    @NotBlank(message = "Old password cannot be blank/null")
    private String oldPassword;

    @Size(min = 3, message = "New password must contain at least 3 characters.")
    @NotBlank(message = "New password cannot be blank/null")
    private String newPassword;
}
