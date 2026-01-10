package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.configuration.LongIdValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasLongId;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LongIdValueTestComparatorTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("LongIdValueTestComparator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class LongIdValueTestComparatorIntegrationTest {

    private HasLongId source;
    private HasLongId target;
    private final LongIdTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.source = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        this.target = HasLongIdTestBuilder.builder()
                .id(2L)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source id is equal to target id")
    void compare_whenSourceIdIsEqualToTargetId_returnsTrue() {
        final Long sourceId = source.getId();
        target.setId(sourceId);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source id is not equal to target id")
    void compare_whenSourceIdIsNotEqualToTargetId_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source id is null")
    void compare_whenSourceIdIsNull_returnsFalse() {
        source.setId(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target id is null")
    void compare_whenTargetIdIsNull_returnsFalse() {
        target.setId(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when source is null")
    void compare_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, target));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when target is null")
    void compare_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(source, null));
    }
}