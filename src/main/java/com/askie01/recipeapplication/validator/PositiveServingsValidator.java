package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasServings;

public class PositiveServingsValidator implements ServingsValidator {

    @Override
    public boolean isValid(HasServings hasServings) {
        return hasPositiveServingsValue(hasServings);
    }

    private boolean hasPositiveServingsValue(HasServings hasServings) {
        return hasServings.getServings() > 0;
    }
}
