package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasServings;

public class PositiveServingsValidator implements ServingsValidator {

    @Override
    public boolean isValid(HasServings hasServings) {
        return hasPositiveServings(hasServings);
    }

    private boolean hasPositiveServings(HasServings hasServings) {
        final Double servings = hasServings.getServings();
        return servings > 0;
    }
}
