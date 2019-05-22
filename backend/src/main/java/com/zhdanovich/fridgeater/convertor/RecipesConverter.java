package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipesConverter {

    private final ProductConverter productConverter;
    private final ProductService productService;

    public RecipeEntity recipeDtoToEntity(final RecipeToSaveDto recipeToSaveDto, final LanguageEntity lang) {
        final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
        recipeNameEntity.setName(recipeToSaveDto.getName());
        recipeNameEntity.setLang(lang);

        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeToSaveDto.isActive());
        recipeEntity.setType(recipeToSaveDto.getType());
        addName(recipeEntity, recipeNameEntity);

        recipeToSaveDto.getProductList().forEach(productToSaveDto -> {
            final ProductEntity productEntity = productService.getProductEntityIfExist(productToSaveDto);
            addProduct(recipeEntity, productEntity != null ? productEntity : productConverter.productToEntity(productToSaveDto, lang));
        });

        return recipeEntity;
    }

    public List<RecipeToSaveDto> recipeEntityToDtoList(final RecipeEntity recipeEntity) {
        final List<RecipeNameEntity> recipeNameEntities = recipeEntity.getRecipeNames();

        return recipeNameEntities.stream().map(recipeNameEntity -> {
            final RecipeToSaveDto dto = new RecipeToSaveDto();
            dto.setLang(recipeNameEntity.getLang().getCode());
            dto.setName(recipeNameEntity.getName());
            dto.setType(recipeEntity.getType());
            dto.setActive(recipeEntity.isActive());
            dto.setId(recipeNameEntity.getId());

            for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
                final List<ProductToSaveDto> productToSaveDtos = productConverter.productEntityToDtoList(productEntity);
                dto.getProductList().addAll(productToSaveDtos.stream().filter(productToSaveDto -> productToSaveDto.getLang().equalsIgnoreCase(dto.getLang())).collect(Collectors.toList()));
            }
            return dto;
        }).collect(Collectors.toList());
    }

    private void addProduct(final RecipeEntity recipeEntity, final ProductEntity productEntity) {
        recipeEntity.getProductEntities().add(productEntity);
        productEntity.getRecipeEntities().add(recipeEntity);
    }

    private void addName(final RecipeEntity recipeEntity, final RecipeNameEntity recipeName) {
        recipeEntity.getRecipeNames().add(recipeName);
        recipeName.setRecipe(recipeEntity);
    }
}