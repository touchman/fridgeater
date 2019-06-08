package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.converter.RecipesConverter;
import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipesConverter recipesConverter;
    private final LanguageService languageService;

    @Override
    public RecipeToSaveDto addRecipe(final RecipeToSaveDto recipeDto) {
        final LanguageEntity lang = languageService.getLanguage(recipeDto.getLang());
        final Optional<RecipeEntity> recipeEntity = recipeRepository.findByNameAndLang(recipeDto.getName(), lang.getId());
        final RecipeEntity entity = recipeEntity.orElseGet(() -> recipesConverter.recipeDtoToEntity(recipeDto, lang));
        recipeDto.setId(entity.getId());

        return recipesConverter.recipeEntityToDtoList(recipeRepository.save(entity)).get(0);
    }

    @Override
    public AllRecipesDto addRecipes(final AllRecipesDto recipesToSaveDto) {
        final AllRecipesDto recipes = new AllRecipesDto();
        recipes.setRecipe(recipesToSaveDto.getRecipe().stream().map(this::addRecipe).collect(Collectors.toCollection(ArrayList::new)));
        return recipes;
    }

    @Override
    public AllRecipesDto getRecipes() {
        final AllRecipesDto allRecipesDto = new AllRecipesDto();
        recipeRepository.findAll().forEach(recipeEntity -> allRecipesDto.getRecipe().addAll(recipesConverter.recipeEntityToDtoList(recipeEntity)));

        return allRecipesDto;
    }
}
