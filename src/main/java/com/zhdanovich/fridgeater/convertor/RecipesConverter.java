package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipesConverter {

    private final ProductConverter productConverter;

    public RecipesConverter() {
        productConverter = new ProductConverter();
    }

    public RecipeEntity recipeDtoToEntity(final RecipeToSaveDto recipeToSaveDTO, final LanguageEntity lang) {
        final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
        recipeNameEntity.setName(recipeToSaveDTO.getName());
        recipeNameEntity.setLang(lang);

        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeToSaveDTO.isActive());
        recipeEntity.setType(recipeToSaveDTO.getType());
        recipeEntity.addName(recipeNameEntity);

        for (final ProductToSaveDto productToSaveDTO : recipeToSaveDTO.getProductList()) {
            recipeEntity.addProduct(productConverter.productToEntity(productToSaveDTO, lang));
        }

        return recipeEntity;
    }

    public List<RecipeToSaveDto> recipeEntityToDtoList(final RecipeEntity recipeEntity) {
        final List<RecipeToSaveDto> recipeToSaveDTO = new ArrayList<>();
        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        for (final RecipeNameEntity recipeNameEntity : recipeNameEntities) {
            final RecipeToSaveDto dto = new RecipeToSaveDto();
            dto.setLang(recipeNameEntity.getLang().getCode());
            dto.setName(recipeNameEntity.getName());
            dto.setType(recipeEntity.getType());
            dto.setActive(recipeEntity.isActive());
            dto.setId(recipeNameEntity.getId());

            for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
                final List<ProductToSaveDto> productToSaveDtos = productConverter.productEntityToDtoList(productEntity);
                dto.getProductList().addAll(productToSaveDtos.stream().filter(productToSaveDTO -> productToSaveDTO.getLang().equalsIgnoreCase(dto.getLang())).collect(Collectors.toList()));
            }

            recipeToSaveDTO.add(dto);
        }


        return recipeToSaveDTO;
    }
}