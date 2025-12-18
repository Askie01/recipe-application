package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultIngredientTestPersistenceChecker implements IngredientTestPersistenceChecker {

    private final LongIdTestPresenceChecker longIdTestPresenceChecker;
    private final StringNameTestPresenceChecker stringNameTestPresenceChecker;
    private final AmountTestPresenceChecker amountTestPresenceChecker;
    private final MeasureUnitTestPersistenceChecker measureUnitTestPersistenceChecker;
    private final SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;
    private final LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @Override
    public boolean wasCreated(Ingredient ingredient) {
        final boolean hasId = hasId(ingredient);
        final boolean hasName = hasName(ingredient);
        final boolean hasAmount = hasAmount(ingredient);
        final boolean hasMeasureUnit = hasMeasureUnit(ingredient);
        final boolean hasCreatedAt = hasCreatedAt(ingredient);
        final boolean hasCreatedBy = hasCreatedBy(ingredient);
        final boolean doesNotHaveUpdatedAt = doesNotHaveUpdatedAt(ingredient);
        final boolean doesNotHaveUpdatedBy = doesNotHaveUpdatedBy(ingredient);
        final boolean hasVersion = hasVersion(ingredient);
        return hasId &&
                hasName &&
                hasAmount &&
                hasMeasureUnit &&
                hasCreatedAt &&
                hasCreatedBy &&
                doesNotHaveUpdatedAt &&
                doesNotHaveUpdatedBy &&
                hasVersion;
    }

    @Override
    public boolean wasUpdated(Ingredient ingredient) {
        final boolean hasId = hasId(ingredient);
        final boolean hasName = hasName(ingredient);
        final boolean hasAmount = hasAmount(ingredient);
        final boolean hasMeasureUnit = hasMeasureUnit(ingredient);
        final boolean hasCreatedAt = hasCreatedAt(ingredient);
        final boolean hasCreatedBy = hasCreatedBy(ingredient);
        final boolean hasUpdatedAt = hasUpdatedAt(ingredient);
        final boolean hasUpdatedBy = hasUpdatedBy(ingredient);
        final boolean hasVersion = hasVersion(ingredient);
        return hasId &&
                hasName &&
                hasAmount &&
                hasMeasureUnit &&
                hasCreatedAt &&
                hasCreatedBy &&
                hasUpdatedAt &&
                hasUpdatedBy &&
                hasVersion;
    }

    private boolean hasId(Ingredient ingredient) {
        return longIdTestPresenceChecker.hasId(ingredient);
    }

    private boolean hasName(Ingredient ingredient) {
        return stringNameTestPresenceChecker.hasName(ingredient);
    }

    private boolean hasAmount(Ingredient ingredient) {
        return amountTestPresenceChecker.hasAmount(ingredient);
    }

    private boolean hasMeasureUnit(Ingredient ingredient) {
        final MeasureUnit measureUnit = ingredient.getMeasureUnit();
        return wasCreated(measureUnit) || wasUpdated(measureUnit);
    }

    private boolean wasCreated(MeasureUnit measureUnit) {
        return measureUnitTestPersistenceChecker.wasCreated(measureUnit);
    }

    private boolean wasUpdated(MeasureUnit measureUnit) {
        return measureUnitTestPersistenceChecker.wasUpdated(measureUnit);
    }

    private boolean hasCreatedAt(Ingredient ingredient) {
        return simpleAuditTestPresenceChecker.hasCreatedAt(ingredient);
    }

    private boolean hasCreatedBy(Ingredient ingredient) {
        return simpleAuditTestPresenceChecker.hasCreatedBy(ingredient);
    }

    private boolean doesNotHaveUpdatedAt(Ingredient ingredient) {
        return !hasUpdatedAt(ingredient);
    }

    private boolean hasUpdatedAt(Ingredient ingredient) {
        return simpleAuditTestPresenceChecker.hasUpdatedAt(ingredient);
    }

    private boolean doesNotHaveUpdatedBy(Ingredient ingredient) {
        return !hasUpdatedBy(ingredient);
    }

    private boolean hasUpdatedBy(Ingredient ingredient) {
        return simpleAuditTestPresenceChecker.hasUpdatedBy(ingredient);
    }

    private boolean hasVersion(Ingredient ingredient) {
        return longVersionTestPresenceChecker.hasVersion(ingredient);
    }
}
