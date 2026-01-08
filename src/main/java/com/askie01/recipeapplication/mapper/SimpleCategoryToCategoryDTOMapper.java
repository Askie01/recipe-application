package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleCategoryToCategoryDTOMapper implements CategoryToCategoryDTOMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public CategoryDTO mapToDTO(Category category) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        map(category, categoryDTO);
        return categoryDTO;
    }

    @Override
    public void map(Category category, CategoryDTO categoryDTO) {
        mapId(category, categoryDTO);
        mapName(category, categoryDTO);
        mapVersion(category, categoryDTO);
    }

    private void mapId(Category category, CategoryDTO categoryDTO) {
        longIdMapper.map(category, categoryDTO);
    }

    private void mapName(Category category, CategoryDTO categoryDTO) {
        stringNameMapper.map(category, categoryDTO);
    }

    private void mapVersion(Category category, CategoryDTO categoryDTO) {
        longVersionMapper.map(category, categoryDTO);
    }
}
