package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.converter.RecipesConverter;
import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.repository.RecipeRepository;
import com.zhdanovich.fridgeater.service.impl.ProductServiceImpl;
import com.zhdanovich.fridgeater.service.impl.RecipeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private LanguageService languageService;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private RecipesConverter recipesConverter;

    @Test
    public void addRecipeTest() {
        final RecipeToSaveDto recipeToSaveDto = MockData.Dto.recipeToSaveDtoEN();

        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();
        final LanguageEntity languageEntity = MockData.Entity.languageEntity("en");

        doReturn(recipeEntity).when(recipeRepository).save(any(RecipeEntity.class));
        doReturn(Optional.ofNullable(recipeEntity)).when(recipeRepository).findByNameAndLang(anyString(), anyLong());
        doReturn(Collections.singletonList(recipeToSaveDto)).when(recipesConverter).recipeEntityToDtoList(any(RecipeEntity.class));
        doReturn(languageEntity).when(languageService).getLanguage(any(String.class));

        recipeService.addRecipe(recipeToSaveDto);

        verify(recipeRepository, times(1)).save(any(RecipeEntity.class));
    }

    @Test
    public void addRecipeAlreadyExist() {
        final RecipeToSaveDto recipeToSaveDto = MockData.Dto.recipeToSaveDtoEN();

        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();

        doReturn(Optional.ofNullable(recipeEntity)).when(recipeRepository).findByNameAndLang(anyString(), anyLong());
        doReturn(recipeEntity).when(recipeRepository).save(any(RecipeEntity.class));
        doReturn(recipeEntity.getRecipeNames().get(0).getLang()).when(languageService).getLanguage(recipeToSaveDto.getLang());
        doReturn(Collections.singletonList(recipeToSaveDto)).when(recipesConverter).recipeEntityToDtoList(any(RecipeEntity.class));

        recipeService.addRecipe(recipeToSaveDto);

        verify(recipeRepository, times(1)).save(any(RecipeEntity.class));
        verify(languageService, times(1)).getLanguage(any(String.class));
    }

    @Test
    public void getAllRecipesTest() {
        final List<RecipeEntity> recipeEntityList = new ArrayList<>();
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();
        recipeEntityList.add(recipeEntity);

        doReturn(recipeEntityList).when(recipeRepository).findAll();
        doReturn(Arrays.asList(MockData.Dto.recipeToSaveDtoEN(), MockData.Dto.recipeToSaveDtoRU())).when(recipesConverter).recipeEntityToDtoList(any(RecipeEntity.class));

        final AllRecipesDto allRecipes = recipeService.getRecipes();

        Assert.assertEquals(2, allRecipes.getRecipe().size());
        for (final RecipeToSaveDto recipe : allRecipes.getRecipe()) {
            Assert.assertTrue(recipe.getProductList().stream().allMatch(productToSaveDto -> productToSaveDto.getLang().equalsIgnoreCase(recipe.getLang())));
        }
    }

    @Test
    public void addRecipesTest() {
        final RecipeToSaveDto recipeToSaveDto = MockData.Dto.recipeToSaveDtoEN();
        final AllRecipesDto recipes = new AllRecipesDto();
        recipes.getRecipe().add(recipeToSaveDto);
        final RecipeEntity recipeEntity = MockData.Entity.recipeEntity();
        final LanguageEntity languageEntity = MockData.Entity.languageEntity("en");

        doReturn(recipeEntity).when(recipeRepository).save(any(RecipeEntity.class));
        doReturn(Optional.ofNullable(recipeEntity)).when(recipeRepository).findByNameAndLang(anyString(), anyLong());
        doReturn(Collections.singletonList(recipeToSaveDto)).when(recipesConverter).recipeEntityToDtoList(any(RecipeEntity.class));
        doReturn(languageEntity).when(languageService).getLanguage(any(String.class));

        recipeService.addRecipes(recipes);

        verify(recipeRepository, times(1)).save(any(RecipeEntity.class));
    }

    @Test
    public void deleteRecipe() {
        final Long recipeId = 1L;
        recipeService.deleteRecipe(recipeId);

        verify(recipeRepository, times(1)).deleteById(recipeId);
    }
}
