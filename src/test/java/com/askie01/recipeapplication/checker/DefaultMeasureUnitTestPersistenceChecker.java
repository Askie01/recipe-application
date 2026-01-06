package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultMeasureUnitTestPersistenceChecker implements MeasureUnitTestPersistenceChecker {

    private final LongIdTestPresenceChecker longIdTestPresenceChecker;
    private final StringNameTestPresenceChecker stringNameTestPresenceChecker;
    private final SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;
    private final LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @Override
    public boolean wasCreated(MeasureUnit measureUnit) {
        final boolean hasId = hasId(measureUnit);
        final boolean hasName = hasName(measureUnit);
        final boolean hasCreatedAt = hasCreatedAt(measureUnit);
        final boolean hasCreatedBy = hasCreatedBy(measureUnit);
        final boolean doesNotHaveUpdatedAt = doesNotHaveUpdatedAt(measureUnit);
        final boolean doesNotHaveUpdatedBy = doesNotHaveUpdatedBy(measureUnit);
        final boolean hasVersion = hasVersion(measureUnit);
        return hasId &&
                hasName &&
                hasCreatedAt &&
                hasCreatedBy &&
                doesNotHaveUpdatedAt &&
                doesNotHaveUpdatedBy &&
                hasVersion;
    }

    @Override
    public boolean wasUpdated(MeasureUnit measureUnit) {
        final boolean hasId = hasId(measureUnit);
        final boolean hasName = hasName(measureUnit);
        final boolean hasCreatedAt = hasCreatedAt(measureUnit);
        final boolean hasCreatedBy = hasCreatedBy(measureUnit);
        final boolean hasUpdatedAt = hasUpdatedAt(measureUnit);
        final boolean hasUpdatedBy = hasUpdatedBy(measureUnit);
        final boolean hasVersion = hasVersion(measureUnit);
        return hasId &&
                hasName &&
                hasCreatedAt &&
                hasCreatedBy &&
                hasUpdatedAt &&
                hasUpdatedBy &&
                hasVersion;
    }

    private boolean hasId(MeasureUnit measureUnit) {
        return longIdTestPresenceChecker.hasId(measureUnit);
    }

    private boolean hasName(MeasureUnit measureUnit) {
        return stringNameTestPresenceChecker.hasName(measureUnit);
    }

    private boolean hasCreatedAt(MeasureUnit measureUnit) {
        return simpleAuditTestPresenceChecker.hasCreatedAt(measureUnit);
    }

    private boolean hasCreatedBy(MeasureUnit measureUnit) {
        return simpleAuditTestPresenceChecker.hasCreatedBy(measureUnit);
    }

    private boolean doesNotHaveUpdatedAt(MeasureUnit measureUnit) {
        return !hasUpdatedAt(measureUnit);
    }

    private boolean hasUpdatedAt(MeasureUnit measureUnit) {
        return simpleAuditTestPresenceChecker.hasUpdatedAt(measureUnit);
    }

    private boolean doesNotHaveUpdatedBy(MeasureUnit measureUnit) {
        return !hasUpdatedBy(measureUnit);
    }

    private boolean hasUpdatedBy(MeasureUnit measureUnit) {
        return simpleAuditTestPresenceChecker.hasUpdatedBy(measureUnit);
    }

    private boolean hasVersion(MeasureUnit measureUnit) {
        return longVersionTestPresenceChecker.hasVersion(measureUnit);
    }
}
