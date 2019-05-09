package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.db.dto.AllRecipesDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.RecipesToGetDTO;

public interface RecipeService {
    RecipeToSaveDTO addRecipe(RecipeToSaveDTO recipeDTO);

    AllRecipesDTO getAllRecipes(RecipesToGetDTO recipesToGetDTO);
}
