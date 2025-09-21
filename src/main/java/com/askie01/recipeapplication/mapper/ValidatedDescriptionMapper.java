package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasDescription;
import com.askie01.recipeapplication.validator.DescriptionValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedDescriptionMapper implements DescriptionMapper {

    private final DescriptionValidator descriptionValidator;

    @Override
    public void map(HasDescription source, HasDescription target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapDescription(source, target);
        }
    }

    private boolean isValid(HasDescription hasDescription) {
        return descriptionValidator.isValid(hasDescription);
    }

    private void mapDescription(HasDescription source, HasDescription target) {
        final String sourceDescription = source.getDescription();
        target.setDescription(sourceDescription);
    }
}
