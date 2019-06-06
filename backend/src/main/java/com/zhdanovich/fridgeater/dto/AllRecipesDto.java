package com.zhdanovich.fridgeater.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllRecipesDto {
    private List<RecipeToSaveDto> recipe = new ArrayList<>();
}
