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
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
        }
    }

    private boolean isValid(HasAmount hasAmount) {
        return amountValidator.isValid(hasAmount);
    }
}
