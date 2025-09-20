package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MeasureUnitMeasureUnitDTOValueTestComparator implements MeasureUnitMeasureUnitDTOTestComparator {

    private final LongIdTestComparator longIdTestComparator;
    private final StringNameTestComparator stringNameTestComparator;
    private final LongVersionTestComparator longVersionTestComparator;

    public boolean compare(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        final boolean haveEqualId = haveEqualId(measureUnit, measureUnitDTO);
        final boolean haveEqualName = haveEqualName(measureUnit, measureUnitDTO);
        final boolean haveEqualVersion = haveEqualVersion(measureUnit, measureUnitDTO);
        return haveEqualId &&
                haveEqualName &&
                haveEqualVersion;
    }

    private boolean haveEqualId(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        return longIdTestComparator.compare(measureUnit, measureUnitDTO);
    }

    private boolean haveEqualName(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        return stringNameTestComparator.compare(measureUnit, measureUnitDTO);
    }

    private boolean haveEqualVersion(MeasureUnit measureUnit, MeasureUnitDTO measureUnitDTO) {
        return longVersionTestComparator.compare(measureUnit, measureUnitDTO);
    }
}
