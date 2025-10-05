package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasInstructions;

import java.util.Objects;

public class InstructionsValueTestComparator implements InstructionsTestComparator {

    @Override
    public boolean compare(HasInstructions source, HasInstructions target) {
        return haveEqualInstructions(source, target);
    }

    private boolean haveEqualInstructions(HasInstructions source, HasInstructions target) {
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        return Objects.equals(sourceInstructions, targetInstructions);
    }
}
