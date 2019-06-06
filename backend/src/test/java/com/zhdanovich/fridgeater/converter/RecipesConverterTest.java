package com.zhdanovich.fridgeater.converter;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import com.zhdanovich.fridgeater.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipesConverterTest {

    @InjectMocks
    private RecipesConverter recipesConverter;

    @Mock
    private ProductService productService;

    @Spy
    private ProductConverter productConverter;

    @Test
    public void recipeEntityToDtoTest() {
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();

        final List<RecipeToSaveDto> recipeToSaveDto = recipesConverter.recipeEntityToDtoList(recipeEntity);

        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        Assert.assertEquals(2, recipeToSaveDto.size());

        int countOfRecipeMatches = 0;
        for (final RecipeToSaveDto recipe : recipeToSaveDto) {
            Assert.assertEquals(recipeEntity.getType(), recipe.getType());
            for (final RecipeNameEntity recipeNameEntity : recipeNameEntities) {
                if (StringUtils.equalsIgnoreCase(recipeNameEntity.getName(), recipe.getName())
                        && StringUtils.equalsIgnoreCase(recipeNameEntity.getLang().getCode(), recipe.getLang())) {
                    countOfRecipeMatches++;
                }
            }
            Assert.assertEquals(recipeEntity.getProductEntities().size(), recipe.getProductList().size());
            Assert.assertTrue(recipe.getProductList().stream().allMatch(productToSaveDto -> productToSaveDto.getLang().equalsIgnoreCase(recipe.getLang())));

        }
        Assert.assertEquals(countOfRecipeMatches, recipeNameEntities.size());
    }

    @Test
    public void recipeDtoToEntityTest() {
        final RecipeToSaveDto recipeToSaveDto = MockData.Dto.recipeToSaveDtoEN();

        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode(recipeToSaveDto.getLang());

        for (final ProductToSaveDto productToSaveDto : recipeToSaveDto.getProductList()) {
            doReturn(
                    MockData.Entity.productEntity(Collections.singletonList(MockData.Entity.productNameEntity(productToSaveDto.getName(), productToSaveDto.getLang()))))
                    .when(productService).getProductEntityIfExist(productToSaveDto);
        }

        final RecipeEntity recipeEntity = recipesConverter.recipeDtoToEntity(recipeToSaveDto, languageEntity);
        final RecipeNameEntity recipeNameEntity = recipeEntity.getRecipeNames().get(0);

        Assert.assertEquals(recipeNameEntity.getLang().getCode(), recipeToSaveDto.getLang());
        Assert.assertEquals(recipeNameEntity.getName(), recipeToSaveDto.getName());
        Assert.assertEquals(recipeNameEntity.getDescription(), recipeToSaveDto.getDescription());
        Assert.assertEquals(recipeEntity.getType(), recipeToSaveDto.getType());
        Assert.assertEquals(recipeEntity.getProductEntities().size(), recipeToSaveDto.getProductList().size());
        for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
            Assert.assertTrue(productEntity.getNameEntity().stream().allMatch(productNameEntity -> productNameEntity.getLang().getCode().equalsIgnoreCase(recipeToSaveDto.getLang())));
        }

        verify(productConverter, times(0)).productToEntity(any(ProductToSaveDto.class), any(LanguageEntity.class));
    }

}