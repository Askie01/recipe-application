package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleMeasureUnitToMeasureUnitDTOMapper implements MeasureUnitToMeasureUnitDTOMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public MeasureUnitDTO mapToDTO(MeasureUnit measureUnit) {
        final MeasureUnitDTO measureUnitDTO = new MeasureUnitDTO();
        map(measureUnit, measureUnitDTO);
        return measureUnitDTO;
    }

    @Override
    public void map(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        mapId(measureUnit, measureUnitDTO);
        mapName(measureUnit, measureUnitDTO);
        mapVersion(measureUnit, measureUnitDTO);
    }

    private void mapId(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        longIdMapper.map(measureUnit, measureUnitDTO);
    }

    private void mapName(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        stringNameMapper.map(measureUnit, measureUnitDTO);
    }

    private void mapVersion(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        longVersionMapper.map(measureUnit, measureUnitDTO);
    }
}
