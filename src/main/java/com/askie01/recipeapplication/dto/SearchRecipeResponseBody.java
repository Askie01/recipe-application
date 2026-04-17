package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class SearchRecipeResponseBody {
    private String owner;
    private Long id;
    private String name;
    private String description;
    private Difficulty difficulty;
    private Set<String> categories;
    private Set<IngredientResponseBody> ingredients;
    private Double servings;
    private Integer cookingTime;
    private String instructions;
}
