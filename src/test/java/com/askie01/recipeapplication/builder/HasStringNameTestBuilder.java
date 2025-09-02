package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasStringName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class HasStringNameTestBuilder implements HasStringName {
    private String name;
}
