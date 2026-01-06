package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasCookingTime;

public class DefaultCookingTimeTestPresenceChecker implements CookingTimeTestPresenceChecker {

    @Override
    public boolean hasCookingTime(HasCookingTime hasCookingTime) {
        return hasPositiveCookingTime(hasCookingTime);
    }

    private boolean hasPositiveCookingTime(HasCookingTime hasCookingTime) {
        final Integer cookingTime = hasCookingTime.getCookingTime();
        return hasNonNullCookingTime(cookingTime) && hasCookingTimeAtLeastOne(cookingTime);
    }

    private boolean hasNonNullCookingTime(Integer cookingTime) {
        return cookingTime != null;
    }

    private boolean hasCookingTimeAtLeastOne(Integer cookingTime) {
        return cookingTime > 0;
    }
}
