package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasLongId;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TestHasLongIdBuilder implements HasLongId {
    private Long id;
}
