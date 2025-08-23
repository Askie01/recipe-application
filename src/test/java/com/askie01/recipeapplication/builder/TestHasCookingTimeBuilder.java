package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasCookingTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TestHasCookingTimeBuilder implements HasCookingTime {
    private Integer cookingTime;
}
