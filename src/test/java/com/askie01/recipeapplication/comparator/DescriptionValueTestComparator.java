package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasDescription;

import java.util.Objects;

public class DescriptionValueTestComparator implements DescriptionTestComparator {

    @Override
    public boolean compare(HasDescription source, HasDescription target) {
        return haveEqualDescription(source, target);
    }

    private boolean haveEqualDescription(HasDescription source, HasDescription target) {
        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        return Objects.equals(sourceDescription, targetDescription);
    }
}
