package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasServings;
import com.askie01.recipeapplication.validator.ServingsValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedServingsMapper implements ServingsMapper {

    private final ServingsValidator servingsValidator;

    @Override
    public void map(HasServings source, HasServings target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapServings(source, target);
        }
    }

    private boolean isValid(HasServings hasServings) {
        return servingsValidator.isValid(hasServings);
    }

    private void mapServings(HasServings source, HasServings target) {
        final Double sourceServings = source.getServings();
        target.setServings(sourceServings);
    }
}
