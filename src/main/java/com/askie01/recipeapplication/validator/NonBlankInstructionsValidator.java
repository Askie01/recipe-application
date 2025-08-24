package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasInstructions;

public class NonBlankInstructionsValidator implements InstructionsValidator {

    @Override
    public boolean isValid(HasInstructions hasInstructions) {
        return hasNonBlankInstructions(hasInstructions);
    }

    private boolean hasNonBlankInstructions(HasInstructions hasInstructions) {
        return !hasBlankInstructions(hasInstructions);
    }

    private boolean hasBlankInstructions(HasInstructions hasInstructions) {
        return hasInstructions.getInstructions().isBlank();
    }
}
