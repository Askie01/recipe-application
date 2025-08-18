package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.model.value.HasStringName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class RecipeDTO implements HasLongId, HasStringName, HasLongVersion {

    @Positive(message = "Id in 'RecipeDTO' have to be a positive number.")
    private Long id;

    @Size(max = 5_242_880, message = "Image in `RecipeDTO` can be up to 5 MBs")
    private byte[] image;

    @NotBlank(message = "Name in `RecipeDTO` can't be empty, blank nor null.")
    private String name;

    @NotBlank(message = "Description in `RecipeDTO` can't be empty, blank nor null.")
    private String description;

    @Valid
    @NotNull(message = "DifficultyDTO in `RecipeDTO` can't be null.")
    private DifficultyDTO difficultyDTO;

    @Valid
    @NotNull(message = "CategoryDTOs in `RecipeDTO` can't be null.")
    private List<CategoryDTO> categoryDTOs;

    @Valid
    @NotEmpty(message = "IngredientDTOs list in `RecipeDTO` can't be empty, nor null.")
    private List<IngredientDTO> ingredientDTOs;

    @DecimalMin(value = "1.0", message = "Servings in `RecipeDTO` have to be at least 1.0.")
    private Double servings;

    @PositiveOrZero(message = "Cooking time in `RecipeDTO` have to be a positive or zero.")
    private Integer cookingTime;

    @NotBlank(message = "Instructions in `RecipeDTO` can't be empty, blank nor null.")
    private String instructions;

    @Positive(message = "Version in 'RecipeDTO' must be a positive number.")
    private Long version;
}
