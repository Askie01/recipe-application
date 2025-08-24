package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.validator.LongVersionValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedLongVersionMapper implements LongVersionMapper {

    private final LongVersionValidator longVersionValidator;

    @Override
    public void map(HasLongVersion source, HasLongVersion target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
        }
    }

    private boolean isValid(HasLongVersion hasLongVersion) {
        return longVersionValidator.isValid(hasLongVersion);
    }
}
