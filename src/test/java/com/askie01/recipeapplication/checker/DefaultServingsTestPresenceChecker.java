package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasServings;

public class DefaultServingsTestPresenceChecker implements ServingsTestPresenceChecker {

    @Override
    public boolean hasServings(HasServings hasServings) {
        return hasPositiveServings(hasServings);
    }

    private boolean hasPositiveServings(HasServings hasServings) {
        final Double servings = hasServings.getServings();
        return hasNonNullServings(servings) && hasNonNegativeServings(servings);
    }

    private boolean hasNonNullServings(Double servings) {
        return servings != null;
    }

    private boolean hasNonNegativeServings(Double servings) {
        return servings > 0;
    }
}
