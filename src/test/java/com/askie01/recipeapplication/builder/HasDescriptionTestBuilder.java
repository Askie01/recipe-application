package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasDescription;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class HasDescriptionTestBuilder implements HasDescription {
    private String description;
}
