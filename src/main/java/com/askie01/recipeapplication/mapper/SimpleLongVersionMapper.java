package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.value.HasLongVersion;

public class SimpleLongVersionMapper implements LongVersionMapper {

    @Override
    public void map(HasLongVersion source, HasLongVersion target) {
        mapVersion(source, target);
    }

    private void mapVersion(HasLongVersion source, HasLongVersion target) {
        final Long sourceVersion = source.getVersion();
        target.setVersion(sourceVersion);
    }
}
