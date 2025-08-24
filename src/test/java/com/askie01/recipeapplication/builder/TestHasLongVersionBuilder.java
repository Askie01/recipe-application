package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasLongVersion;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TestHasLongVersionBuilder implements HasLongVersion {
    private Long version;
}
