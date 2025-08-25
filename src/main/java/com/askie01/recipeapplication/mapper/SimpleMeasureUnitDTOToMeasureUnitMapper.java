package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleMeasureUnitDTOToMeasureUnitMapper implements MeasureUnitDTOToMeasureUnitMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public MeasureUnit mapToEntity(MeasureUnitDTO measureUnitDTO) {
        final MeasureUnit measureUnit = new MeasureUnit();
        map(measureUnitDTO, measureUnit);
        return measureUnit;
    }

    @Override
    public void map(MeasureUnitDTO measureUnitDTO, MeasureUnit measureUnit) {
        mapId(measureUnitDTO, measureUnit);
        mapName(measureUnitDTO, measureUnit);
        mapVersion(measureUnitDTO, measureUnit);
    }

    private void mapId(MeasureUnitDTO measureUnitDTO, MeasureUnit measureUnit) {
        longIdMapper.map(measureUnitDTO, measureUnit);
    }

    private void mapName(MeasureUnitDTO measureUnitDTO, MeasureUnit measureUnit) {
        stringNameMapper.map(measureUnitDTO, measureUnit);
    }

    private void mapVersion(MeasureUnitDTO measureUnitDTO, MeasureUnit measureUnit) {
        longVersionMapper.map(measureUnitDTO, measureUnit);
    }
}
