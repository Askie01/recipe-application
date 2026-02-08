package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "image")
@EqualsAndHashCode
public class UserRecipeResponseBody {
    private String username;
    private Long id;
    private String name;
    private byte[] image;
    private String description;
    private Difficulty difficulty;
    private Set<String> categories;
    private Set<IngredientResponseBody> ingredients;
    private Double servings;
    private Integer cookingTime;
    private String instructions;
}
