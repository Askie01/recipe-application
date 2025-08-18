package com.askie01.recipeapplication.mapper;

public interface ToValueMapper <Source, Value> {
    Value mapToValue(Source source);
}
