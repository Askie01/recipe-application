package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasInstructions;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class HasInstructionsTestBuilder implements HasInstructions {
    private String instructions;
}
