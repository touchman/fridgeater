package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dbo.RecipeNameEntity;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RecipesConverterTest {

    private final RecipesConverter recipesConverter = new RecipesConverter(new ProductConverter());

    @Test
    public void recipeEntityToDtoTest() {
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();

        final List<RecipeToSaveDTO> recipeToSaveDTO = recipesConverter.recipeEntityToDtoList(recipeEntity);

        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        Assert.assertEquals(2, recipeToSaveDTO.size());

        int countOfMatches = 0;
        for (final RecipeToSaveDTO recipe : recipeToSaveDTO) {
            Assert.assertEquals(recipe.getType(), recipeEntity.getType().name());
            for (final RecipeNameEntity recipeNameEntity : recipeNameEntities) {
                if (StringUtils.equalsIgnoreCase(recipeNameEntity.getName(), recipe.getName())
                        && StringUtils.equalsIgnoreCase(recipeNameEntity.getLang().getCode(), recipe.getLang())) {
                    countOfMatches++;
                }
            }
            Assert.assertEquals(recipe.getProductList().size(), recipeEntity.getProductEntities().size() / recipeToSaveDTO.size());
            Assert.assertTrue(recipe.getProductList().stream().allMatch(productToSaveDTO -> productToSaveDTO.getLang().equalsIgnoreCase(recipe.getLang())));

        }
        Assert.assertEquals(countOfMatches, recipeNameEntities.size());
    }

    @Test
    public void recipeDtoToEntityTest() {
        final RecipeToSaveDTO recipeToSaveDTO = MockData.Dto.recipeToSaveDTO();

        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode(recipeToSaveDTO.getLang());

        final RecipeEntity recipeEntity = recipesConverter.recipeDtoToEntity(recipeToSaveDTO, languageEntity);
        final RecipeNameEntity recipeNameEntity = recipeEntity.getRecipeNames().get(0);

        Assert.assertEquals(recipeNameEntity.getLang().getCode(), recipeToSaveDTO.getLang());
        Assert.assertEquals(recipeNameEntity.getName(), recipeToSaveDTO.getName());
        Assert.assertEquals(recipeEntity.getType().name(), recipeToSaveDTO.getType());
        Assert.assertEquals(recipeEntity.getProductEntities().size(), 1);
    }
}