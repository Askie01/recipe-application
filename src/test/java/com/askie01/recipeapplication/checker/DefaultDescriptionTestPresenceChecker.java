package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasDescription;

public class DefaultDescriptionTestPresenceChecker implements DescriptionTestPresenceChecker {

    @Override
    public boolean hasDescription(HasDescription hasDescription) {
        return hasNonBlankDescription(hasDescription);
    }

    private boolean hasNonBlankDescription(HasDescription hasDescription) {
        final String description = hasDescription.getDescription();
        return descriptionIsNotNull(description) && descriptionIsNonBlank(description);
    }

    private boolean descriptionIsNotNull(String description) {
        return description != null;
    }

    private boolean descriptionIsNonBlank(String description) {
        return !description.isBlank();
    }
}
