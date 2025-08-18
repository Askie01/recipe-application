package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.entity.BaseEntity;

public interface ToEntityMapper <Source, Entity extends BaseEntity<?>> {
    Entity mapToEntity(Source source);
}
