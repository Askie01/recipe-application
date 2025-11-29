package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasLongId;

public class DefaultLongIdTestPresenceChecker implements LongIdTestPresenceChecker {

    @Override
    public boolean hasId(HasLongId hasLongId) {
        return hasPositiveId(hasLongId);
    }

    private boolean hasPositiveId(HasLongId hasLongId) {
        final Long id = hasLongId.getId();
        return id != null && id > 0;
    }
}
