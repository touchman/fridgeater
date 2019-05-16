package com.zhdanovich.fridgeater.repository;

import com.zhdanovich.fridgeater.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

}
