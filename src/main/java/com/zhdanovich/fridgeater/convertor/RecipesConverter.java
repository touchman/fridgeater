package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import org.springframework.stereotype.Service;

@Service
public class RecipesConverter {
    public RecipeEntity recipeDtoToEntity(final RecipeToSaveDTO recipeToSaveDTO) {
        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeToSaveDTO.isActive());
        recipeEntity.setType(RecipeTypeEnum.valueOf(recipeToSaveDTO.getType()));

        return recipeEntity;
    }

    public RecipeToSaveDTO recipeEntityToDto(final RecipeEntity recipeEntity) {
        final RecipeToSaveDTO recipeToSaveDTO = new RecipeToSaveDTO();
        recipeToSaveDTO.setActive(recipeEntity.isActive());
        recipeToSaveDTO.setType(recipeEntity.getType().name());

        return recipeToSaveDTO;
    }
}