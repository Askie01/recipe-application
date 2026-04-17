package com.askie01.recipeapplication.repository;

import com.askie01.recipeapplication.model.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepositoryV3 extends JpaRepository<Recipe, Long> {

    @Query("""
            SELECT r
            FROM Customer c
            JOIN c.recipes r
            WHERE c.username = :username
            """)
    Page<Recipe> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("""
            SELECT DISTINCT r
            FROM Recipe r
            JOIN r.categories c
            JOIN r.ingredients i
            JOIN i.measureUnit m
            WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(r.difficulty) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(r.instructions) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Recipe> searchRecipes(@Param("query") String query, Pageable pageable);

    @Query("""
            SELECT c.username
            FROM Customer c
            JOIN c.recipes r
            WHERE r.id = :id
            """)
    Optional<String> findOwner(@Param("id") Long id);

    @Query("""
            SELECT r.image
            FROM Recipe r
            WHERE r.id = :id
            """)
    Optional<byte[]> findRecipeImage(@Param("id") Long id);
}
