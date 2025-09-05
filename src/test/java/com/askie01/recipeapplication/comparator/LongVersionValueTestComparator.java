package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasLongVersion;

import java.util.Objects;

public class LongVersionValueTestComparator implements LongVersionTestComparator {

    @Override
    public boolean compare(HasLongVersion source, HasLongVersion target) {
        return haveEqualVersion(source, target);
    }

    private boolean haveEqualVersion(HasLongVersion source, HasLongVersion target) {
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        return Objects.equals(sourceVersion, targetVersion);
    }
}
