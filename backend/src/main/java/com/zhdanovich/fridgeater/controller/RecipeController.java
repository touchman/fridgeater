package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.dto.AllRecipesDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity saveRecipe(@RequestBody final RecipeToSaveDto recipeToSaveDto) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.addRecipe(recipeToSaveDto));
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public ResponseEntity saveRecipes(@RequestBody final AllRecipesDto recipesToSaveDto) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.addRecipes(recipesToSaveDto));
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public ResponseEntity getRecipes() {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipes());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRecipe(@PathVariable final Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.status(HttpStatus.OK).body("Recipe with id=" + id + " has been deleted");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRecipe(@RequestBody final RecipeToSaveDto recipeToSaveDto, @PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.updateRecipe(recipeToSaveDto, id));
    }
}
