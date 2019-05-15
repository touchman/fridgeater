package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dto.AllRecipesDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import com.zhdanovich.fridgeater.helper.LanguageHelper;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipesConverter recipesConverter;
    private final LanguageHelper languageHelper;

    @Override
    public RecipeToSaveDTO addRecipe(final RecipeToSaveDTO recipeDTO) {
        final LanguageEntity lang = languageHelper.getLanguage(recipeDTO.getLang());
        final RecipeEntity entity = recipeRepository.save(recipesConverter.recipeDtoToEntity(recipeDTO, lang));
        recipeDTO.setId(entity.getId());

        return recipesConverter.recipeEntityToDtoList(entity).get(0);
    }

    @Override
    public AllRecipesDTO getAllRecipes() {
        final List<RecipeEntity> allRecipeEntities = recipeRepository.findAll();
        final AllRecipesDTO allRecipesDTO = new AllRecipesDTO();
        for (final RecipeEntity recipeEntity : allRecipeEntities) {
            allRecipesDTO.getAllRecipes().addAll(recipesConverter.recipeEntityToDtoList(recipeEntity));
        }

        return allRecipesDTO;
    }


}
