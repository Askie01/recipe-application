package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.common.LongIdentifiable;
import com.askie01.recipeapplication.model.common.LongVersionable;
import com.askie01.recipeapplication.model.common.StringNameable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "Id in 'IngredientDTO' have to be a positive number.")
    private Long id;

    @NotBlank(message = "Name in `IngredientDTO` can't be empty, blank nor null.")
    private String name;

    @DecimalMin(value = "0.1", message = "Amount in `IngredientDTO` have to be at least 0.1")
    private Double amount;

    @NotNull(message = "MeasureUnitDTO in `IngredientDTO` can't be null.")
    private MeasureUnitDTO measureUnitDTO;

    @Positive(message = "Version in 'IngredientDTO' must be a positive number.")
    private Long version;
}
