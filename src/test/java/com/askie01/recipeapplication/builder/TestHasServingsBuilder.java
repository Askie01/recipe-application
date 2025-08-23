package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasServings;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TestHasServingsBuilder implements HasServings {
    private Double servings;
}
