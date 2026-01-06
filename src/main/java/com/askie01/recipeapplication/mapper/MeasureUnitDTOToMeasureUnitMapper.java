package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.MeasureUnit;

public interface MeasureUnitDTOToMeasureUnitMapper extends
        Mapper<MeasureUnitDTO, MeasureUnit>,
        ToEntityMapper<MeasureUnitDTO, MeasureUnit> {
}
