package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.db.dto.PersistedRecipeDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;

public interface RecipeService {
    PersistedRecipeDTO addRecipe(RecipeToSaveDTO recipeDTO);
}
