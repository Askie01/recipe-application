package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasStringName;
import com.askie01.recipeapplication.validator.StringNameValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedStringNameMapper implements StringNameMapper {

    private final StringNameValidator stringNameValidator;

    @Override
    public void map(HasStringName source, HasStringName target) {
        final boolean isValid = isValid(source);
        if (isValid) {
            final String sourceName = source.getName();
            target.setName(sourceName);
        }
    }

    private boolean isValid(HasStringName hasStringName) {
        return stringNameValidator.isValid(hasStringName);
    }
}
