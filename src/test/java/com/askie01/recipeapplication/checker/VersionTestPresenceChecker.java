package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasVersion;

public interface VersionTestPresenceChecker <Versionable extends HasVersion<?>> {
    boolean hasVersion(Versionable versionable);
}
