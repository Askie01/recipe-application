package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasLongVersion;

public class DefaultLongVersionTestPresenceChecker implements LongVersionTestPresenceChecker {

    @Override
    public boolean hasVersion(HasLongVersion hasLongVersion) {
        return hasNonNegativeVersion(hasLongVersion);
    }

    private boolean hasNonNegativeVersion(HasLongVersion hasLongVersion) {
        final Long version = hasLongVersion.getVersion();
        return versionIsNotNull(version) && versionIsAtLeastZero(version);
    }

    private boolean versionIsNotNull(Long version) {
        return version != null;
    }

    private boolean versionIsAtLeastZero(Long version) {
        return version >= 0;
    }
}
