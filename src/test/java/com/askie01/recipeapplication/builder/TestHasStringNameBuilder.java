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
public class TestHasStringNameBuilder implements HasStringName {
    private String name;
}
