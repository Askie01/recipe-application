package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.model.entity.Category;

public interface CategoryDTOToCategoryMapper extends
        Mapper<CategoryDTO, Category>,
        ToEntityMapper<CategoryDTO, Category> {
}
