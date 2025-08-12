package com.askie01.recipeapplication.repository;

import com.askie01.recipeapplication.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
