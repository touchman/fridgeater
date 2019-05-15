package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.db.dto.AllRecipesDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;

public interface RecipeService {
    RecipeToSaveDTO addRecipe(RecipeToSaveDTO recipeDTO);

    AllRecipesDTO getAllRecipes();
}
