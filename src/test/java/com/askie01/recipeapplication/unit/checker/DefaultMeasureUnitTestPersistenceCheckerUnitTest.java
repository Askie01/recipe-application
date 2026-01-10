package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.checker.*;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMeasureUnitTestPersistenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultMeasureUnitTestPersistenceCheckerUnitTest {

    private MeasureUnit source;
    private MeasureUnitTestPersistenceChecker checker;

    @Mock
    private LongIdTestPresenceChecker longIdTestPresenceChecker;

    @Mock
    private StringNameTestPresenceChecker stringNameTestPresenceChecker;

    @Mock
    private SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;

    @Mock
    private LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultMeasureUnitTestPersistenceChecker(
                longIdTestPresenceChecker,
                stringNameTestPresenceChecker,
                simpleAuditTestPresenceChecker,
                longVersionTestPresenceChecker
        );
        this.source = new RandomMeasureUnitTestFactory(Faker.instance()).createMeasureUnit();
    }

    @Test
    @DisplayName("wasCreated method should return true when source has updatedAt and updatedBy fields null.")
    void wasCreated_whenSourceHasUpdateAtAndUpdatedByNull_returnsTrue() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        when(longIdTestPresenceChecker.hasId(source)).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(source)).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(source)).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(source)).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source has updatedAt and updatedBy fields not null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(source)).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(source)).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(source)).thenReturn(true);
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
    @DisplayName("wasUpdated method should throw NullPointerException when source is null.")
    void wasUpdated_whenSourceIsNull_throwsNullPointerException() {
        when(longIdTestPresenceChecker.hasId(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> checker.wasUpdated(null));
    }
}