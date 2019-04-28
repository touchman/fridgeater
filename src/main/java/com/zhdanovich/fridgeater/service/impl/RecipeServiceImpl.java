package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dto.PersistedRecipeDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Override
    public PersistedRecipeDTO addRecipe(RecipeToSaveDTO recipeDTO) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeDTO.isActive());
        recipeEntity.setType(recipeDTO.getType());
        recipeRepository.save(recipeEntity);
        PersistedRecipeDTO persistedRecipeDTO = new PersistedRecipeDTO();
        persistedRecipeDTO.setStatus("OK");
        return persistedRecipeDTO;
    }
}
