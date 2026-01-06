package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasStringName;

import java.util.Objects;

public class StringNameValueTestComparator implements StringNameTestComparator {

    @Override
    public boolean compare(HasStringName source, HasStringName target) {
        return haveEqualName(source, target);
    }

    private boolean haveEqualName(HasStringName source, HasStringName target) {
        final String sourceName = source.getName();
        final String targetName = target.getName();
        return Objects.equals(sourceName, targetName);
    }
}
