package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.db.dto.PersistedRecipeDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
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

    private RecipeService recipeService;

    @RequestMapping(method = RequestMethod.POST)
    public PersistedRecipeDTO saveRecipe(@RequestBody final RecipeToSaveDTO recipeToSaveDTO) {
        return recipeService.addRecipe(recipeToSaveDTO);
    }
}
