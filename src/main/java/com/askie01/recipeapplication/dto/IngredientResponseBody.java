package com.askie01.recipeapplication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class IngredientResponseBody {
    private String name;
    private Double amount;
    private String unit;
}
