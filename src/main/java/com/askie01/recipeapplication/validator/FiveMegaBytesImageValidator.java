package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasImage;

public class FiveMegaBytesImageValidator implements ImageValidator {

    private static final int FIVE_MEGA_BYTES = 5 * 1024 * 1024;

    @Override
    public boolean isValid(HasImage hasImage) {
        return hasImageSizeUpToFiveMegaBytes(hasImage);
    }

    private boolean hasImageSizeUpToFiveMegaBytes(HasImage hasImage) {
        final int imageSize = hasImage.getImage().length;
        return imageSize <= FIVE_MEGA_BYTES;
    }
}
