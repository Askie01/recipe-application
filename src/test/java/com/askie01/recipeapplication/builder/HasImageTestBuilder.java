package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class HasImageTestBuilder implements HasImage {
    private byte[] image;
}
