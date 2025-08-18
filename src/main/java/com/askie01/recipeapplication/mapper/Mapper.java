package com.askie01.recipeapplication.mapper;

public interface Mapper <Source, Target> {
    void map(Source source, Target target);
}
