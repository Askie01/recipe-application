package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasStringName;
import com.askie01.recipeapplication.validator.StringNameValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedStringNameMapper implements StringNameMapper {

    private final StringNameValidator stringNameValidator;

    @Override
    public void map(HasStringName source, HasStringName target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapName(source, target);
        }
    }

    private boolean isValid(HasStringName hasStringName) {
        return stringNameValidator.isValid(hasStringName);
    }

    private void mapName(HasStringName source, HasStringName target) {
        final String sourceName = source.getName();
        target.setName(sourceName);
    }
}
