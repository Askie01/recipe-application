package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCategoryTestPersistenceChecker implements CategoryTestPersistenceChecker {

    private final LongIdTestPresenceChecker longIdTestPresenceChecker;
    private final StringNameTestPresenceChecker stringNameTestPresenceChecker;
    private final SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;
    private final LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @Override
    public boolean wasCreated(Category category) {
        final boolean hasId = hasId(category);
        final boolean hasName = hasName(category);
        final boolean hasCreatedAt = hasCreatedAt(category);
        final boolean hasCreatedBy = hasCreatedBy(category);
        final boolean doesNotHaveUpdatedAt = doesNotHaveUpdatedAt(category);
        final boolean doesNotHaveUpdatedBy = doesNotHaveUpdatedBy(category);
        final boolean hasVersion = hasVersion(category);
        return hasId &&
                hasName &&
                hasCreatedAt &&
                hasCreatedBy &&
                doesNotHaveUpdatedAt &&
                doesNotHaveUpdatedBy &&
                hasVersion;
    }

    @Override
    public boolean wasUpdated(Category category) {
        final boolean hasId = hasId(category);
        final boolean hasName = hasName(category);
        final boolean hasCreatedAt = hasCreatedAt(category);
        final boolean hasCreatedBy = hasCreatedBy(category);
        final boolean hasUpdatedAt = hasUpdatedAt(category);
        final boolean hasUpdatedBy = hasUpdatedBy(category);
        final boolean hasVersion = hasVersion(category);
        return hasId &&
                hasName &&
                hasCreatedAt &&
                hasCreatedBy &&
                hasUpdatedAt &&
                hasUpdatedBy &&
                hasVersion;
    }

    private boolean hasId(Category category) {
        return longIdTestPresenceChecker.hasId(category);
    }

    private boolean hasName(Category category) {
        return stringNameTestPresenceChecker.hasName(category);
    }

    private boolean hasCreatedAt(Category category) {
        return simpleAuditTestPresenceChecker.hasCreatedAt(category);
    }

    private boolean hasCreatedBy(Category category) {
        return simpleAuditTestPresenceChecker.hasCreatedBy(category);
    }

    private boolean doesNotHaveUpdatedAt(Category category) {
        return !hasUpdatedAt(category);
    }

    private boolean hasUpdatedAt(Category category) {
        return simpleAuditTestPresenceChecker.hasUpdatedAt(category);
    }

    private boolean doesNotHaveUpdatedBy(Category category) {
        return !hasUpdatedBy(category);
    }

    private boolean hasUpdatedBy(Category category) {
        return simpleAuditTestPresenceChecker.hasUpdatedBy(category);
    }

    private boolean hasVersion(Category category) {
        return longVersionTestPresenceChecker.hasVersion(category);
    }
}
