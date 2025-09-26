package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedLongIdMapper implements LongIdMapper {

    private final LongIdValidator longIdValidator;

    @Override
    public void map(HasLongId source, HasLongId target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapId(source, target);
        }
    }

    private boolean isValid(HasLongId hasLongId) {
        return longIdValidator.isValid(hasLongId);
    }

    private void mapId(HasLongId source, HasLongId target) {
        final Long sourceId = source.getId();
        target.setId(sourceId);
    }
}
