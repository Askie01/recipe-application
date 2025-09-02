package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveLongIdValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedLongIdMapperConfiguration;
import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.model.value.HasLongId;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedLongIdMapperConfiguration.class,
        PositiveLongIdValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.id-type=validated-long-id",
        "component.validator.id-type=positive-long-id"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedLongIdMapper integration tests")
class ValidatedLongIdMapperIntegrationTest {

    private final LongIdMapper mapper;
    private HasLongId source;
    private HasLongId target;

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
    @DisplayName("map method should map source id to target id when source id is valid")
    void map_whenSourceIdIsValid_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should not map source id to target id when source id is invalid")
    void map_whenSourceIdIsInvalid_doesNotMapSourceIdToTargetId() {
        final Long targetIdBeforeMapping = target.getId();
        source.setId(-1L);
        mapper.map(source, target);
        final Long targetIdAfterMapping = target.getId();
        assertEquals(targetIdBeforeMapping, targetIdAfterMapping);
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