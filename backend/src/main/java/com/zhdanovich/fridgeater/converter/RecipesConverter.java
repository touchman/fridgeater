package com.zhdanovich.fridgeater.converter;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipesConverter {

    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    public RecipeEntity recipeDtoToEntity(final RecipeToSaveDto recipeToSaveDto, final LanguageEntity lang) {
        final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
        recipeNameEntity.setName(recipeToSaveDto.getName());
        recipeNameEntity.setDescription(recipeToSaveDto.getDescription());
        recipeNameEntity.setLang(lang);

        final RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setActive(recipeToSaveDto.isActive());
        recipeEntity.setType(recipeToSaveDto.getType());
        addName(recipeEntity, recipeNameEntity);

        processProductDtos(recipeToSaveDto, lang, recipeEntity, this);

        recipeToSaveDto.getProductList().forEach(productToSaveDto -> {
            final Optional<ProductEntity> productEntity = productRepository.findByNameAndLang(productToSaveDto.getName(), lang.getId());
            addProduct(recipeEntity, productEntity.orElseGet(() -> productConverter.productToEntity(productToSaveDto, lang)));
        });

        return recipeEntity;
    }

    public void processProductDtos(final RecipeToSaveDto recipeToSaveDto, final LanguageEntity lang, final RecipeEntity recipeEntity, final RecipesConverter recipesConverter) {
        recipeToSaveDto.getProductList().forEach(productToSaveDto -> {
            final Optional<ProductEntity> productEntity = productRepository.findByNameAndLang(productToSaveDto.getName(), lang.getId());
            recipesConverter.addProduct(recipeEntity, productEntity.orElseGet(() -> productConverter.productToEntity(productToSaveDto, lang)));
        });
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
            dto.setDescription(recipeNameEntity.getDescription());

            for (final ProductEntity productEntity : recipeEntity.getProductEntities()) {
                final List<ProductToSaveDto> productToSaveDtos = productConverter.productEntityToDtoList(productEntity);
                dto.getProductList().addAll(productToSaveDtos.stream().filter(productToSaveDto -> productToSaveDto.getLang().equalsIgnoreCase(dto.getLang())).collect(Collectors.toList()));
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public void addProduct(final RecipeEntity recipeEntity, final ProductEntity productEntity) {
        recipeEntity.getProductEntities().add(productEntity);
        productEntity.getRecipeEntities().add(recipeEntity);
    }

    public void removeProduct(final RecipeEntity recipeEntity, final ProductEntity productEntity) {
        recipeEntity.getProductEntities().remove(productEntity);
        productEntity.getRecipeEntities().remove(recipeEntity);
    }

    private void addName(final RecipeEntity recipeEntity, final RecipeNameEntity recipeName) {
        recipeEntity.getRecipeNames().add(recipeName);
        recipeName.setRecipe(recipeEntity);
    }
}