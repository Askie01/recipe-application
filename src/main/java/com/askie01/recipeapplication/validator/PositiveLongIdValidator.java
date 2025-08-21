package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasLongId;

public class PositiveLongIdValidator implements LongIdValidator {

    @Override
    public boolean isValid(HasLongId hasLongId) {
        return hasPositiveId(hasLongId);
    }

    private boolean hasPositiveId(HasLongId hasLongId) {
        return hasLongId.getId() > 0;
    }
}
