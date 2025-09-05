package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCategoryDTOValueTestComparator implements CategoryCategoryDTOTestComparator {

    private final LongIdTestComparator longIdTestComparator;
    private final StringNameTestComparator stringNameTestComparator;
    private final LongVersionTestComparator longVersionTestComparator;

    @Override
    public boolean compare(Category category, CategoryDTO categoryDTO) {
        final boolean haveEqualId = haveEqualId(category, categoryDTO);
        final boolean haveEqualName = haveEqualName(category, categoryDTO);
        final boolean haveEqualVersion = haveEqualVersion(category, categoryDTO);
        return haveEqualId &&
                haveEqualName &&
                haveEqualVersion;
    }

    private boolean haveEqualId(Category category, CategoryDTO categoryDTO) {
        return longIdTestComparator.compare(category, categoryDTO);
    }

    private boolean haveEqualName(Category category, CategoryDTO categoryDTO) {
        return stringNameTestComparator.compare(category, categoryDTO);
    }

    private boolean haveEqualVersion(Category category, CategoryDTO categoryDTO) {
        return longVersionTestComparator.compare(category, categoryDTO);
    }
}
