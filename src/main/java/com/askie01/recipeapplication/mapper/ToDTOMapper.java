package com.askie01.recipeapplication.mapper;

public interface ToDTOMapper <Source, DTO> {
    DTO mapToDTO(Source source);
}
