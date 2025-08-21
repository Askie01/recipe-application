package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.TestHasLongIdBuilder;
import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongIdMapper;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedLongIdMapper unit tests")
class ValidatedLongIdMapperUnitTest {

    @Mock
    private LongIdValidator longIdValidator;
    private LongIdMapper mapper;
    private HasLongId source;
    private HasLongId target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedLongIdMapper(longIdValidator);
        this.source = TestHasLongIdBuilder.builder()
                .id(1L)
                .build();
        this.target = TestHasLongIdBuilder.builder()
                .id(2L)
                .build();
    }

    @Test
    @DisplayName("map method should map source id to target id when source id is valid")
    void map_whenSourceIdIsValid_mapsSourceIdToTargetId() {
        when(longIdValidator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should not map source id to target id when source id is invalid")
    void map_whenSourceIdIsInvalid_doesNotMapSourceIdToTargetId() {
        when(longIdValidator.isValid(source)).thenReturn(false);
        final Long targetIdBeforeMapping = target.getId();
        mapper.map(source, target);
        final Long targetIdAfterMapping = target.getId();
        assertEquals(targetIdBeforeMapping, targetIdAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        when(longIdValidator.isValid(source)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        when(longIdValidator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}