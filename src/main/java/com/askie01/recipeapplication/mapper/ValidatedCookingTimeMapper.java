package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasCookingTime;
import com.askie01.recipeapplication.validator.CookingTimeValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedCookingTimeMapper implements CookingTimeMapper {

    private final CookingTimeValidator cookingTimeValidator;

    @Override
    public void map(HasCookingTime source, HasCookingTime target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            final Integer sourceCookingTime = source.getCookingTime();
            target.setCookingTime(sourceCookingTime);
        }
    }

    private boolean isValid(HasCookingTime hasCookingTime) {
        return cookingTimeValidator.isValid(hasCookingTime);
    }
}
