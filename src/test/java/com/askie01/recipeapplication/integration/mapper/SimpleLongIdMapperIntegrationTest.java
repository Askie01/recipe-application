package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.configuration.LongIdMapperConfiguration;
import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.model.value.HasLongId;
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

@SpringJUnitConfig(classes = LongIdMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.long-id-type=simple")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleLongIdMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleLongIdMapperIntegrationTest {

    private HasLongId source;
    private HasLongId target;
    private final LongIdMapper mapper;

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
    @DisplayName("map method should map source id to target id when source and target are present")
    void map_shouldMapSourceIdToTargetId_whenSourceAndTargetArePresent() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
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