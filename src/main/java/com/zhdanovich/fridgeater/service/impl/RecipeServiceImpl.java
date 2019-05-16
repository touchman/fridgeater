package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipesConverter recipesConverter;
    private final LanguageService languageService;

    @Override
    public RecipeToSaveDto addRecipe(final RecipeToSaveDto recipeDTO) {
        final LanguageEntity lang = languageService.getLanguage(recipeDTO.getLang());
        final RecipeEntity entity = recipeRepository.save(recipesConverter.recipeDtoToEntity(recipeDTO, lang));
        recipeDTO.setId(entity.getId());

        return recipesConverter.recipeEntityToDtoList(entity).get(0);
    }

    @Override
    public AllRecipesDto getAllRecipes() {
        final List<RecipeEntity> allRecipeEntities = recipeRepository.findAll();
        final AllRecipesDto allRecipesDTO = new AllRecipesDto();
        for (final RecipeEntity recipeEntity : allRecipeEntities) {
            allRecipesDTO.getAllRecipes().addAll(recipesConverter.recipeEntityToDtoList(recipeEntity));
        }

        return allRecipesDTO;
    }


}
