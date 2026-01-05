package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.comparator.DescriptionTestComparator;
import com.askie01.recipeapplication.configuration.DescriptionValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasDescription;
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
@ContextConfiguration(classes = DescriptionValueTestComparatorTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DescriptionValueTestComparator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DescriptionValueTestComparatorIntegrationTest {

    private HasDescription source;
    private HasDescription target;
    private final DescriptionTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.source = HasDescriptionTestBuilder.builder()
                .description("source description")
                .build();
        this.target = HasDescriptionTestBuilder.builder()
                .description("target description")
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source description is equal to target description")
    void compare_whenSourceDescriptionIsEqualToTargetDescription_returnsTrue() {
        final String sourceDescription = source.getDescription();
        target.setDescription(sourceDescription);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source description is not equal to target description")
    void compare_whenSourceDescriptionIsNotEqualToTargetDescription_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source description is null")
    void compare_whenSourceDescriptionIsNull_returnsFalse() {
        source.setDescription(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target description is null")
    void compare_whenTargetDescriptionIsNull_returnsFalse() {
        target.setDescription(null);
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