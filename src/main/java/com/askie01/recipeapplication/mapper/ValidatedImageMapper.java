package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasImage;
import com.askie01.recipeapplication.validator.ImageValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedImageMapper implements ImageMapper {

    private final ImageValidator imageValidator;

    @Override
    public void map(HasImage source, HasImage target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapImage(source, target);
        }
    }

    private boolean isValid(HasImage hasImage) {
        return imageValidator.isValid(hasImage);
    }

    private void mapImage(HasImage source, HasImage target) {
        final byte[] sourceImage = source.getImage();
        target.setImage(sourceImage);
    }
}
