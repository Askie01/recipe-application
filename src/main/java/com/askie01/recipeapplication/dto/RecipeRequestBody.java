package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.entity.value.Difficulty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RecipeRequestBody {

    @NotBlank(message = "Recipe name cannot be blank/null")
    private String name;

    @Size(max = 5_242_880, message = "Recipe image cannot be larger than 5 MBs")
    private byte[] image;

    @NotBlank(message = "Recipe description cannot be blank/null")
    private String description;

    @NotNull(message = "Recipe difficulty has to be EASY/MEDIUM/HARD")
    private Difficulty difficulty;

    @NotEmpty(message = "Recipe categories cannot be empty")
    private Set<String> categories;

    @Valid
    @NotEmpty(message = "Recipe ingredients cannot be empty")
    private Set<IngredientRequestBody> ingredients;

    @DecimalMin(value = "1.0", message = "Recipe servings have to be at least 1.0")
    private Double servings;

    @PositiveOrZero(message = "Recipe cooking time have to be a positive or zero number")
    private Integer cookingTime;

    @NotBlank(message = "Recipe instructions cannot be blank/null")
    private String instructions;
}
