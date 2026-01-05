package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasSimpleAuditTestBuilder;
import com.askie01.recipeapplication.checker.DefaultSimpleAuditTestPresenceChecker;
import com.askie01.recipeapplication.checker.SimpleAuditTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasSimpleAudit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultSimpleAuditTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultSimpleAuditTestPresenceCheckerUnitTest {

    private HasSimpleAudit source;
    private SimpleAuditTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultSimpleAuditTestPresenceChecker();
        this.source = HasSimpleAuditTestBuilder.builder()
                .createdAt(LocalDateTime.now().minusDays(1))
                .createdBy("createdBy")
                .updatedAt(LocalDateTime.now().minusDays(2))
                .updatedBy("updatedBy")
                .build();
    }

    @Test
    @DisplayName("hasCreatedAt method should return true when source createdAt is before current time")
    void hasCreatedAt_whenSourceCreatedAtIsBeforeCurrentTime_returnsTrue() {
        final boolean result = checker.hasCreatedAt(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasCreatedAt method should return false when source createdAt is after current time")
    void hasCreatedAt_whenSourceCreatedAtIsAfterCurrentTime_returnsFalse() {
        final LocalDateTime nextDay = LocalDateTime.now().plusDays(1);
        source.setCreatedAt(nextDay);
        final boolean result = checker.hasCreatedAt(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCreatedAt method should return false when source createdAt is null")
    void hasCreatedAt_whenSourceCreatedAtIsNull_returnsFalse() {
        source.setCreatedAt(null);
        final boolean result = checker.hasCreatedAt(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCreatedAt method should throw NullPointerException when source is null")
    void hasCreatedAt_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasCreatedAt(null));
    }

    @Test
    @DisplayName("hasCreatedBy method should return true when source createdBy is present")
    void hasCreatedBy_whenSourceCreatedByIsPresent_returnsTrue() {
        final boolean result = checker.hasCreatedBy(source);
        assertTrue(result);
    }

    @ParameterizedTest(name = "hasCreatedBy method should return false when source createdBy is blank")
    @ValueSource(strings = {"", " "})
    void hasCreatedBy_whenSourceCreatedByIsBlank_returnsFalse(String createdBy) {
        source.setCreatedBy(createdBy);
        final boolean result = checker.hasCreatedBy(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCreatedBy method should return false when source createdBy is null")
    void hasCreatedBy_whenSourceCreatedByIsNull_returnsFalse() {
        source.setCreatedBy(null);
        final boolean result = checker.hasCreatedBy(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCreatedBy method should throw NullPointerException when source is null")
    void hasCreatedBy_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasCreatedBy(null));
    }

    @Test
    @DisplayName("hasUpdatedAt method should return true when source updatedAt is before current time")
    void hasUpdatedAt_whenSourceUpdatedAtIsBeforeCurrentTime_returnsTrue() {
        final boolean result = checker.hasUpdatedAt(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasUpdatedAt method should return false when source updatedAt is after current time")
    void hasUpdatedAt_whenSourceUpdatedAtIsAfterCurrentTime_returnsFalse() {
        final LocalDateTime nextDay = LocalDateTime.now().plusDays(1);
        source.setUpdatedAt(nextDay);
        final boolean result = checker.hasUpdatedAt(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasUpdatedAt method should return false when source updatedAt is null")
    void hasUpdatedAt_whenSourceUpdatedAtIsNull_returnsFalse() {
        source.setUpdatedAt(null);
        final boolean result = checker.hasUpdatedAt(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasUpdatedAt method should throw NullPointerException when source is null")
    void hasUpdatedAt_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasUpdatedAt(null));
    }

    @Test
    @DisplayName("hasUpdatedBy method should return true when source updatedBy is present")
    void hasUpdatedBy_whenSourceUpdatedByIsPresent_returnsTrue() {
        final boolean result = checker.hasUpdatedBy(source);
        assertTrue(result);
    }

    @ParameterizedTest(name = "hasUpdatedBy method should return false when source updatedBy is blank")
    @ValueSource(strings = {"", " "})
    void hasUpdatedBy_whenSourceUpdatedByIsBlank_returnsFalse(String updatedBy) {
        source.setUpdatedBy(updatedBy);
        final boolean result = checker.hasUpdatedBy(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasUpdatedBy method should return false when source updatedBy is null")
    void hasUpdatedBy_whenSourceUpdatedByIsNull_returnsFalse() {
        source.setUpdatedBy(null);
        final boolean result = checker.hasUpdatedBy(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasUpdatedBy method should throw NullPointerException when source is null")
    void hasUpdatedBy_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasUpdatedBy(null));
    }
}