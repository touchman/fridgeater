package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.db.dto.AllRecipesDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.RecipesToGetDTO;
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
    public RecipeToSaveDTO saveRecipe(@RequestBody final RecipeToSaveDTO recipeToSaveDTO) {
        return recipeService.addRecipe(recipeToSaveDTO);
    }

    @RequestMapping(value = "/getAllRecipes", method = RequestMethod.POST)
    public AllRecipesDTO getAllProducts(@RequestBody final RecipesToGetDTO recipesToGetDTO) {
        return recipeService.getAllRecipes(recipesToGetDTO);
    }

}
