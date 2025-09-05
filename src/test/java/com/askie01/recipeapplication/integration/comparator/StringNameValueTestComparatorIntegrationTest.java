package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.comparator.StringNameTestComparator;
import com.askie01.recipeapplication.configuration.StringNameValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasStringName;
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
@ContextConfiguration(classes = {
        StringNameValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("StringNameValueTestComparator integration tests")
class StringNameValueTestComparatorIntegrationTest {

    private final StringNameTestComparator comparator;
    private HasStringName source;
    private HasStringName target;

    @BeforeEach
    void setUp() {
        this.source = HasStringNameTestBuilder.builder()
                .name("source name")
                .build();
        this.target = HasStringNameTestBuilder.builder()
                .name("target name")
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source name is equal to target name")
    void compare_whenSourceNameIsEqualToTargetName_returnsTrue() {
        final String sourceName = source.getName();
        target.setName(sourceName);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source name is not equal to target name")
    void compare_whenSourceNameIsNotEqualToTargetName_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source name is null")
    void compare_whenSourceNameIsNull_returnsFalse() {
        source.setName(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target name is null")
    void compare_whenTargetNameIsNull_returnsFalse() {
        target.setName(null);
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