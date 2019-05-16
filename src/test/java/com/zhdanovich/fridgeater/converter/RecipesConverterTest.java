package com.zhdanovich.fridgeater.converter;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RecipesConverterTest {

    private final RecipesConverter recipesConverter = new RecipesConverter(new ProductConverter());

    @Test
    public void recipeEntityToDtoTest() {
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();

        final List<RecipeToSaveDto> recipeToSaveDTO = recipesConverter.recipeEntityToDtoList(recipeEntity);

        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        Assert.assertEquals(2, recipeToSaveDTO.size());

        int countOfRecipeMatches = 0;
        for (final RecipeToSaveDto recipe : recipeToSaveDTO) {
            Assert.assertEquals(recipeEntity.getType(), recipe.getType());
            for (final RecipeNameEntity recipeNameEntity : recipeNameEntities) {
                if (StringUtils.equalsIgnoreCase(recipeNameEntity.getName(), recipe.getName())
                        && StringUtils.equalsIgnoreCase(recipeNameEntity.getLang().getCode(), recipe.getLang())) {
                    countOfRecipeMatches++;
                }
            }
            Assert.assertEquals(recipeEntity.getProductEntities().size(), recipe.getProductList().size());
            Assert.assertTrue(recipe.getProductList().stream().allMatch(productToSaveDTO -> productToSaveDTO.getLang().equalsIgnoreCase(recipe.getLang())));

        }
        Assert.assertEquals(countOfRecipeMatches, recipeNameEntities.size());
    }

    @Test
    public void recipeDtoToEntityTest() {
        final RecipeToSaveDto recipeToSaveDTO = MockData.Dto.recipeToSaveDtoEN();

        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode(recipeToSaveDTO.getLang());

        final RecipeEntity recipeEntity = recipesConverter.recipeDtoToEntity(recipeToSaveDTO, languageEntity);
        final RecipeNameEntity recipeNameEntity = recipeEntity.getRecipeNames().get(0);

        Assert.assertEquals(recipeNameEntity.getLang().getCode(), recipeToSaveDTO.getLang());
        Assert.assertEquals(recipeNameEntity.getName(), recipeToSaveDTO.getName());
        Assert.assertEquals(recipeEntity.getType(), recipeToSaveDTO.getType());
        Assert.assertEquals(recipeEntity.getProductEntities().size(), recipeToSaveDTO.getProductList().size());
        for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
            Assert.assertTrue(productEntity.getNameEntity().stream().allMatch(productNameEntity -> productNameEntity.getLang().getCode().equalsIgnoreCase(recipeToSaveDTO.getLang())));
        }
    }

}