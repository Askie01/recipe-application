package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.model.entity.Category;

public interface CategoryToCategoryDTOMapper
        extends Mapper<Category, CategoryDTO>,
        ToDTOMapper<Category, CategoryDTO> {
}
