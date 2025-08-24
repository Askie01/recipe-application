package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasLongVersion;

public class PositiveLongVersionValidator implements LongVersionValidator {

    @Override
    public boolean isValid(HasLongVersion hasLongVersion) {
        return hasPositiveVersion(hasLongVersion);
    }

    private boolean hasPositiveVersion(HasLongVersion hasLongVersion) {
        return hasLongVersion.getVersion() > 0;
    }
}
