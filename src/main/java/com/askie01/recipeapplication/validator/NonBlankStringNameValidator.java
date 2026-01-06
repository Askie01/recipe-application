package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasStringName;

public class NonBlankStringNameValidator implements StringNameValidator {

    @Override
    public boolean isValid(HasStringName hasStringName) {
        return hasNonBlankName(hasStringName);
    }

    private boolean hasNonBlankName(HasStringName hasStringName) {
        return !hasBlankName(hasStringName);
    }

    private boolean hasBlankName(HasStringName hasStringName) {
        final String name = hasStringName.getName();
        return name.isBlank();
    }
}
