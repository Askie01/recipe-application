package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.common.LongIdentifiable;
import com.askie01.recipeapplication.model.common.LongVersionable;
import com.askie01.recipeapplication.model.common.StringNameable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class IngredientDTO implements LongIdentifiable, StringNameable, LongVersionable {
    private Long id;
    private String name;
    private Double amount;
    private MeasureUnitDTO measureUnitDTO;
    private Long version;
}
