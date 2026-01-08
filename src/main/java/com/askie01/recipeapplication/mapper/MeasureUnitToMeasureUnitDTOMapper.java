package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.MeasureUnit;

public interface MeasureUnitToMeasureUnitDTOMapper
        extends Mapper<MeasureUnit, MeasureUnitDTO>,
        ToDTOMapper<MeasureUnit, MeasureUnitDTO> {
}
