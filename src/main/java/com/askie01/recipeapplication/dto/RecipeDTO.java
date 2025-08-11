package com.askie01.recipeapplication.dto;

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
public class RecipeDTO {
    private Long id;
    private byte[] image;
    private String name;
    private String description;
    private DifficultyDTO difficultyDTO;
    private List<CategoryDTO> categoryDTOs;
    private List<IngredientDTO> ingredientDTOs;
    private Double servings;
    private Integer cookingTime;
    private String instructions;
    private Long version;
}
