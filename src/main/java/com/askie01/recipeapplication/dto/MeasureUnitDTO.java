package com.askie01.recipeapplication.dto;

import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.model.value.HasStringName;
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
public class MeasureUnitDTO implements HasLongId, HasStringName, HasLongVersion {

    @Positive(message = "Id in 'MeasureUnitDTO' have to be a positive number.")
    private Long id;

    @NotBlank(message = "Name in 'MeasureUnitDTO' can't be empty, blank nor null.")
    private String name;

    @Positive(message = "Version in 'MeasureUnitDTO' must be a positive number.")
    private Long version;
}
