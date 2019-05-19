package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipesConverter recipesConverter;
    private final LanguageService languageService;

    @Override
    public RecipeToSaveDto addRecipe(final RecipeToSaveDto recipeDto) {
        final LanguageEntity lang = languageService.getLanguage(recipeDto.getLang());
        final RecipeEntity recipeEntity = getRecipeIfExist(recipeDto);
        final RecipeEntity entity = recipeEntity != null ? recipeEntity : recipesConverter.recipeDtoToEntity(recipeDto, lang);
        recipeDto.setId(entity.getId());
        //recipeDto.getProductList().forEach(productService::addProduct);

        return recipesConverter.recipeEntityToDtoList(recipeRepository.save(entity)).get(0);
    }

    @Override
    public AllRecipesDto getRecipes() {
        final List<RecipeEntity> allRecipeEntities = recipeRepository.findAll();
        final AllRecipesDto allRecipesDto = new AllRecipesDto();
        for (final RecipeEntity recipeEntity : allRecipeEntities) {
            allRecipesDto.getAllRecipes().addAll(recipesConverter.recipeEntityToDtoList(recipeEntity));
        }

        return allRecipesDto;
    }

    public RecipeEntity getRecipeIfExist(final RecipeToSaveDto recipeDto) {
        final List<RecipeEntity> allRecipeEntities = recipeRepository.findAll();
        RecipeEntity recipeEntity = null;
        for (final RecipeEntity entity : allRecipeEntities) {
            final Optional<RecipeNameEntity> foundEntity = entity.getRecipeNames().stream().filter(recipeNameEntity -> recipeNameEntity.getLang().getCode().equalsIgnoreCase(recipeDto.getLang()))
                    .filter(recipeNameEntity -> recipeNameEntity.getName().equals(recipeDto.getName())).findAny();
            if (foundEntity.isPresent()) {
                recipeEntity = entity;
                break;
            }
        }
        return recipeEntity;
    }


}
