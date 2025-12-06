package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasImage;

public class DefaultImageTestPresenceChecker implements ImageTestPresenceChecker {

    @Override
    public boolean hasImage(HasImage hasImage) {
        final byte[] image = hasImage.getImage();
        return image != null && image.length > 0;
    }
}
