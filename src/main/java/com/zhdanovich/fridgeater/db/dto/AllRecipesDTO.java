package com.zhdanovich.fridgeater.db.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllRecipesDTO {
    private List<RecipeToSaveDTO> allRecipes = new ArrayList<>();
}
