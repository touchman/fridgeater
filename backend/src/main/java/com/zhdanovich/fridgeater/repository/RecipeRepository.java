package com.zhdanovich.fridgeater.repository;

import com.zhdanovich.fridgeater.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    @Query("SELECT r FROM recipe r JOIN r.recipeNames rn on r.id = rn.recipe.id WHERE rn.name = :recipeName AND rn.lang.id = :langId")
    Optional<RecipeEntity> findByNameAndLang(@Param("recipeName") String recipeName,
                                             @Param("langId") Long langId);
}
