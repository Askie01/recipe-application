package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasCookingTime;

public class PositiveCookingTimeValidator implements CookingTimeValidator {

    @Override
    public boolean isValid(HasCookingTime hasCookingTime) {
        return hasPositiveCookingTime(hasCookingTime);
    }

    private boolean hasPositiveCookingTime(HasCookingTime hasCookingTime) {
        return hasCookingTime.getCookingTime() > 0;
    }
}
