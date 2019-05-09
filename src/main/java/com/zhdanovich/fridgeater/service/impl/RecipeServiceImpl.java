package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
import com.zhdanovich.fridgeater.db.dto.AllRecipesDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.RecipesToGetDTO;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipesConverter recipesConverter;

    @Override
    public RecipeToSaveDTO addRecipe(final RecipeToSaveDTO recipeDTO) {
        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeDTO.isActive());
        recipeEntity.setType(RecipeTypeEnum.valueOf(recipeDTO.getType()));
        final RecipeEntity entity = recipeRepository.save(recipeEntity);
        recipeDTO.setId(entity.getId());

        return recipeDTO;
    }

    @Override
    public AllRecipesDTO getAllRecipes(final RecipesToGetDTO recipesToGetDTO) {
        final List<RecipeEntity> allProducts = recipeRepository.findAll();
        final AllRecipesDTO allRecipesDTO = new AllRecipesDTO();
        allRecipesDTO.setAllProducts(allProducts.stream().map(recipesConverter::recipeEntityToDto).collect(Collectors.toList()));
        return allRecipesDTO;
    }


}
