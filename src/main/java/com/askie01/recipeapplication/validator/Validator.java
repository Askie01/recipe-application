package com.askie01.recipeapplication.validator;

public interface Validator <Argument> {
    boolean isValid(Argument argument);
}
