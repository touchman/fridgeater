package com.zhdanovich.fridgeater.converter;

import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @RequestMapping(method = RequestMethod.POST)
    public RecipeToSaveDto saveRecipe(@RequestBody final RecipeToSaveDto recipeToSaveDTO) {
        return recipeService.addRecipe(recipeToSaveDTO);
    }

    @RequestMapping(value = "/getAllRecipes", method = RequestMethod.POST)
    public AllRecipesDto getAllProducts() {
        return recipeService.getAllRecipes();
    }

}
