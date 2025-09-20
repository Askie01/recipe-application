package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasCookingTime;

import java.util.Objects;

public class CookingTimeValueTestComparator implements CookingTimeTestComparator {

    @Override
    public boolean compare(HasCookingTime source, HasCookingTime target) {
        return haveEqualCookingTime(source, target);
    }

    private boolean haveEqualCookingTime(HasCookingTime source, HasCookingTime target) {
        final Integer sourceCookingTime = source.getCookingTime();
        final Integer targetCookingTime = target.getCookingTime();
        return Objects.equals(sourceCookingTime, targetCookingTime);
    }
}
