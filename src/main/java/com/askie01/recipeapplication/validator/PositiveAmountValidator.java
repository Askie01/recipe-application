package com.askie01.recipeapplication.validator;

import com.askie01.recipeapplication.model.value.HasAmount;

public class PositiveAmountValidator implements AmountValidator {

    @Override
    public boolean isValid(HasAmount hasAmount) {
        return hasPositiveAmount(hasAmount);
    }

    private boolean hasPositiveAmount(HasAmount hasAmount) {
        return hasAmount.getAmount() > 0;
    }
}
