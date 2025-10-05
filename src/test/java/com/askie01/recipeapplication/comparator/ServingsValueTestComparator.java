package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasServings;

import java.util.Objects;

public class ServingsValueTestComparator implements ServingsTestComparator {

    @Override
    public boolean compare(HasServings source, HasServings target) {
        return haveEqualServings(source, target);
    }

    private boolean haveEqualServings(HasServings source, HasServings target) {
        final Double sourceServings = source.getServings();
        final Double targetServings = target.getServings();
        return Objects.equals(sourceServings, targetServings);
    }
}
