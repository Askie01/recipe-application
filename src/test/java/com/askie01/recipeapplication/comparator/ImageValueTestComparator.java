package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasImage;

import java.util.Arrays;

public class ImageValueTestComparator implements ImageTestComparator {

    @Override
    public boolean compare(HasImage source, HasImage target) {
        return haveEqualImage(source, target);
    }

    private boolean haveEqualImage(HasImage source, HasImage target) {
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        return Arrays.equals(sourceImage, targetImage);
    }
}
