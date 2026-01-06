package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.checker.*;
import com.askie01.recipeapplication.factory.RandomIngredientTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasSimpleAudit;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultIngredientTestPersistenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultIngredientTestPersistenceCheckerUnitTest {

    private Ingredient source;
    private IngredientTestPersistenceChecker checker;

    @Mock
    private LongIdTestPresenceChecker longIdTestPresenceChecker;

    @Mock
    private StringNameTestPresenceChecker stringNameTestPresenceChecker;

    @Mock
    private AmountTestPresenceChecker amountTestPresenceChecker;

    @Mock
    private MeasureUnitTestPersistenceChecker measureUnitTestPersistenceChecker;

    @Mock
    private SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;

    @Mock
    private LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultIngredientTestPersistenceChecker(
                longIdTestPresenceChecker,
                stringNameTestPresenceChecker,
                amountTestPresenceChecker,
                measureUnitTestPersistenceChecker,
                simpleAuditTestPresenceChecker,
                longVersionTestPresenceChecker
        );
        final Faker faker = new Faker();
        this.source = new RandomIngredientTestFactory(faker, new RandomMeasureUnitTestFactory(faker)).createIngredient();
    }

    @Test
    @DisplayName("wasCreated method should return true when source has updatedAt and updatedBy fields null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsTrue() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(amountTestPresenceChecker.hasAmount(any(Ingredient.class))).thenReturn(true);
        when(measureUnitTestPersistenceChecker.wasCreated(any(MeasureUnit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source has updatedAt and updatedBy fields not null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(amountTestPresenceChecker.hasAmount(any(Ingredient.class))).thenReturn(true);
        when(measureUnitTestPersistenceChecker.wasCreated(any(MeasureUnit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when measureUnit was not created.")
    void wasCreated_whenSourceMeasureUnitWasNotCreated_returnsFalse() {
        source.getMeasureUnit().setId(null);
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(amountTestPresenceChecker.hasAmount(any(Ingredient.class))).thenReturn(true);
        when(measureUnitTestPersistenceChecker.wasCreated(any(MeasureUnit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should throw NullPointerException when source is null.")
    void wasCreated_whenSourceIsNull_throwsNullPointerException() {
        when(longIdTestPresenceChecker.hasId(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> checker.wasCreated(null));
    }

    @Test
    @DisplayName("wasUpdated method should return true when source has updatedAt and updatedBy fields not null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsTrue() {
        when(longIdTestPresenceChecker.hasId(source)).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(source)).thenReturn(true);
        when(amountTestPresenceChecker.hasAmount(any(Ingredient.class))).thenReturn(true);
        when(measureUnitTestPersistenceChecker.wasCreated(any(MeasureUnit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(source)).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source has updatedAt and updatedBy fields null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsFalse() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        when(longIdTestPresenceChecker.hasId(source)).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(source)).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(source)).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when measureUnit was not created.")
    void wasUpdated_whenSourceMeasureUnitWasNotCreated_returnsFalse() {
        source.getMeasureUnit().setId(null);
        when(longIdTestPresenceChecker.hasId(source)).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(amountTestPresenceChecker.hasAmount(any(Ingredient.class))).thenReturn(true);
        when(measureUnitTestPersistenceChecker.wasCreated(any(MeasureUnit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should throw NullPointerException when source is null.")
    void wasUpdated_whenSourceIsNull_throwsNullPointerException() {
        when(longIdTestPresenceChecker.hasId(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> checker.wasUpdated(null));
    }
}