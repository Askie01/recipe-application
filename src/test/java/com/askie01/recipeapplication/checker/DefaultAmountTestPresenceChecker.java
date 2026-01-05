package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasAmount;

public class DefaultAmountTestPresenceChecker implements AmountTestPresenceChecker {

    @Override
    public boolean hasAmount(HasAmount hasAmount) {
        return hasPositiveAmount(hasAmount);
    }

    private boolean hasPositiveAmount(HasAmount hasAmount) {
        final Double amount = hasAmount.getAmount();
        return isNotNull(amount) && isOverZero(amount);
    }

    private boolean isNotNull(Double amount) {
        return amount != null;
    }

    private boolean isOverZero(Double amount) {
        return amount > 0;
    }
}
