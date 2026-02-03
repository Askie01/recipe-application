package com.askie01.recipeapplication.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class IngredientRequestBody {

    @NotBlank(message = "Ingredient name cannot be blank/null")
    private String name;

    @DecimalMin(value = "0.1", message = "Ingredient amount have to be at least 0.1")
    private Double amount;

    @NotBlank(message = "Ingredient unit cannot be blank/null")
    private String unit;
}
