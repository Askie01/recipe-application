package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasStringName;

public class DefaultStringNameTestPresenceChecker implements StringNameTestPresenceChecker {

    @Override
    public boolean hasName(HasStringName hasStringName) {
        return hasNonBlankName(hasStringName);
    }

    private boolean hasNonBlankName(HasStringName hasStringName) {
        final String name = hasStringName.getName();
        return nameIsNotNull(name) && nameIsNonBlank(name);
    }

    private boolean nameIsNotNull(String name) {
        return name != null;
    }

    private boolean nameIsNonBlank(String name) {
        return !name.isBlank();
    }
}
