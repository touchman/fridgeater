package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.RecipesConverter;
import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.impl.RecipeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private LanguageService languageService;

    @Spy
    private RecipesConverter recipesConverter;

    @Test
    public void addRecipeTest() {
        final RecipeToSaveDto recipeToSaveDTO = MockData.Dto.recipeToSaveDtoEN();

        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();

        doReturn(recipeEntity).when(recipeRepository).save(any(RecipeEntity.class));

        recipeService.addRecipe(recipeToSaveDTO);

        verify(recipeRepository, times(1)).save(any(RecipeEntity.class));
    }

    @Test
    public void getAllRecipesTest() {
        final List<RecipeEntity> recipeEntityList = new ArrayList<>();
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();
        recipeEntityList.add(recipeEntity);

        doReturn(recipeEntityList).when(recipeRepository).findAll();

        final AllRecipesDto allRecipes = recipeService.getAllRecipes();

        Assert.assertEquals(2, allRecipes.getAllRecipes().size());
        for (final RecipeToSaveDto recipe : allRecipes.getAllRecipes()) {
            Assert.assertTrue(recipe.getProductList().stream().allMatch(productToSaveDTO -> productToSaveDTO.getLang().equalsIgnoreCase(recipe.getLang())));
        }
    }
}
