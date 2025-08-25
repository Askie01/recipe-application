package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasAmount;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TestHasAmountBuilder implements HasAmount {
    private Double amount;
}
