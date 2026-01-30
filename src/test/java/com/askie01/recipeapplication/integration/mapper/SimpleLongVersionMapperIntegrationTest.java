package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.configuration.LongVersionMapperConfiguration;
import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = LongVersionMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.version-type=simple-long-version")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleLongVersionMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleLongVersionMapperIntegrationTest {

    private HasLongVersion source;
    private HasLongVersion target;
    private final LongVersionMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(2L)
                .build();
    }

    @Test
    @DisplayName("map method should map source version to target version when source and target are present")
    void map_shouldMapSourceVersionToTargetVersion_whenSourceAndTargetArePresent() {
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}