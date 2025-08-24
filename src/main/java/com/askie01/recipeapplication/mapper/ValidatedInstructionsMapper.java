package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasInstructions;
import com.askie01.recipeapplication.validator.InstructionsValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedInstructionsMapper implements InstructionsMapper {

    private final InstructionsValidator instructionsValidator;

    @Override
    public void map(HasInstructions source, HasInstructions target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            final String sourceInstructions = source.getInstructions();
            target.setInstructions(sourceInstructions);
        }
    }

    private boolean isValid(HasInstructions hasInstructions) {
        return instructionsValidator.isValid(hasInstructions);
    }
}
