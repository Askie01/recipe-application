package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasAmount;

import java.util.Objects;

public class AmountValueTestComparator implements AmountTestComparator {

    @Override
    public boolean compare(HasAmount source, HasAmount target) {
        return haveEqualAmount(source, target);
    }

    private boolean haveEqualAmount(HasAmount source, HasAmount target) {
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        return Objects.equals(sourceAmount, targetAmount);
    }
}
