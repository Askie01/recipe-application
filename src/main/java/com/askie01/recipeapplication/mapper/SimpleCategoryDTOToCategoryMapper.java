package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleCategoryDTOToCategoryMapper implements CategoryDTOToCategoryMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public Category mapToEntity(CategoryDTO categoryDTO) {
        final Category category = new Category();
        map(categoryDTO, category);
        return category;
    }

    @Override
    public void map(CategoryDTO categoryDTO, Category category) {
        mapId(categoryDTO, category);
        mapName(categoryDTO, category);
        mapVersion(categoryDTO, category);
    }

    private void mapId(CategoryDTO categoryDTO, Category category) {
        longIdMapper.map(categoryDTO, category);
    }

    private void mapName(CategoryDTO categoryDTO, Category category) {
        stringNameMapper.map(categoryDTO, category);
    }

    private void mapVersion(CategoryDTO categoryDTO, Category category) {
        longVersionMapper.map(categoryDTO, category);
    }
}
