package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.validator.AmountValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedAmountMapper implements AmountMapper {

    private final AmountValidator amountValidator;

    @Override
    public void map(HasAmount source, HasAmount target) {
        final boolean sourceIsValid = isValid(source);
        if (sourceIsValid) {
            mapAmount(source, target);
        }
    }

    private boolean isValid(HasAmount hasAmount) {
        return amountValidator.isValid(hasAmount);
    }

    private void mapAmount(HasAmount source, HasAmount target) {
        final Double sourceAmount = source.getAmount();
        target.setAmount(sourceAmount);
    }
}
