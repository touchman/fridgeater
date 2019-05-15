package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dbo.RecipeEntity;
import com.zhdanovich.fridgeater.db.dbo.RecipeNameEntity;
import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipesConverter {

    private final ProductConverter productConverter;

    public RecipesConverter() {
        productConverter = new ProductConverter();
    }

    public RecipeEntity recipeDtoToEntity(final RecipeToSaveDTO recipeToSaveDTO, final LanguageEntity lang) {
        final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
        recipeNameEntity.setName(recipeToSaveDTO.getName());
        recipeNameEntity.setLang(lang);

        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeToSaveDTO.isActive());
        recipeEntity.setType(RecipeTypeEnum.valueOf(recipeToSaveDTO.getType()));
        recipeEntity.addName(recipeNameEntity);

        for (final ProductToSaveDTO productToSaveDTO : recipeToSaveDTO.getProductList()) {
            recipeEntity.addProduct(productConverter.productToEntity(productToSaveDTO, lang));
        }

        return recipeEntity;
    }

    public List<RecipeToSaveDTO> recipeEntityToDtoList(final RecipeEntity recipeEntity) {
        final List<RecipeToSaveDTO> recipeToSaveDTO = new ArrayList<>();
        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        for (final RecipeNameEntity recipeNameEntity : recipeNameEntities) {
            final RecipeToSaveDTO dto = new RecipeToSaveDTO();
            dto.setLang(recipeNameEntity.getLang().getCode());
            dto.setName(recipeNameEntity.getName());
            dto.setType(recipeEntity.getType().name());
            dto.setActive(recipeEntity.isActive());
            dto.setId(recipeNameEntity.getId());

            for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
                dto.getProductList().addAll(productConverter.productEntityToDtoList(productEntity));
            }

            recipeToSaveDTO.add(dto);
        }


        return recipeToSaveDTO;
    }
}