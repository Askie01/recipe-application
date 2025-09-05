package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.model.value.HasLongId;

import java.util.Objects;

public class LongIdValueTestComparator implements LongIdTestComparator {

    @Override
    public boolean compare(HasLongId source, HasLongId target) {
        return haveEqualId(source, target);
    }

    private boolean haveEqualId(HasLongId source, HasLongId target) {
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        return Objects.equals(sourceId, targetId);
    }
}
