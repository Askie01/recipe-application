package com.askie01.recipeapplication.repository;

import com.askie01.recipeapplication.model.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepositoryV2 extends JpaRepository<Recipe, Long> {

    @Query(
            value = """
                    SELECT DISTINCT r
                    FROM Recipe r
                    LEFT JOIN r.ingredients
                    LEFT JOIN r.categories
                    ORDER BY r.id ASC
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT r.id)
                    FROM Recipe r
                    LEFT JOIN r.ingredients i
                    LEFT JOIN r.categories c
                    """
    )
    Page<Recipe> findDistinctRecipes(Pageable pageable);

    @Query("""
                FROM Recipe r
                WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :text, '%'))
                   OR LOWER(r.description) LIKE LOWER(CONCAT('%', :text, '%'))
            """)
    Page<Recipe> search(@Param("text") String text, Pageable pageable);
}
