package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasDescription;

public class NonBlankDescriptionValidator implements DescriptionValidator {

    @Override
    public boolean isValid(HasDescription hasDescription) {
        return hasNonBlankDescription(hasDescription);
    }

    private boolean hasNonBlankDescription(HasDescription hasDescription) {
        return !hasBlankDescription(hasDescription);
    }


    private boolean hasBlankDescription(HasDescription hasDescription) {
        final String description = hasDescription.getDescription();
        return description.isBlank();
    }
}
