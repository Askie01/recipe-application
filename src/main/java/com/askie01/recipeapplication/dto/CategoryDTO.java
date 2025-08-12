package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.common.LongIdentifiable;
import com.askie01.recipeapplication.model.common.LongVersionable;
import com.askie01.recipeapplication.model.common.StringNameable;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDTO implements LongIdentifiable, StringNameable, LongVersionable {

    @Positive(message = "Id in 'CategoryDTO' have to be a positive number.")
    private Long id;

    @NotBlank(message = "Name in 'CategoryDTO' can't be empty, blank nor null.")
    private String name;

    @Positive(message = "Version in 'CategoryDTO' must be a positive number.")
    private Long version;
}
