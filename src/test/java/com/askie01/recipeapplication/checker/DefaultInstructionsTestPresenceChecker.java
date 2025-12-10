package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasInstructions;

public class DefaultInstructionsTestPresenceChecker implements InstructionsTestPresenceChecker {

    @Override
    public boolean hasInstructions(HasInstructions hasInstructions) {
        return hasNonBlankInstructions(hasInstructions);
    }

    private boolean hasNonBlankInstructions(HasInstructions hasInstructions) {
        final String instructions = hasInstructions.getInstructions();
        return instructionsAreNotNull(instructions) && instructionsAreNonBlank(instructions);
    }

    private boolean instructionsAreNotNull(String instructions) {
        return instructions != null;
    }

    private boolean instructionsAreNonBlank(String instructions) {
        return !instructions.isBlank();
    }
}
