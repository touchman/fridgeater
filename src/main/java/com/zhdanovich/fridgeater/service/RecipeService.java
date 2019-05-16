package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;

public interface RecipeService {
    RecipeToSaveDto addRecipe(RecipeToSaveDto recipeDTO);

    AllRecipesDto getAllRecipes();
}
