package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasLongId;

public class SimpleLongIdMapper implements LongIdMapper {

    @Override
    public void map(HasLongId source, HasLongId target) {
        mapId(source, target);
    }

    private void mapId(HasLongId source, HasLongId target) {
        final Long sourceId = source.getId();
        target.setId(sourceId);
    }
}
