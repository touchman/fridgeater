package com.zhdanovich.fridgeater;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.ProductNameEntity;
import com.zhdanovich.fridgeater.entity.RecipeEntity;
import com.zhdanovich.fridgeater.entity.RecipeNameEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockData {

    public static class Entity {

        private static Long productEntityId = 1L;
        private static Long productEntityNameId = 1L;
        private static Long recipeEntityNameId = 1L;
        private static Long languageEntityNameId = 1L;

        public static ProductEntity productEntity() {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setId(productEntityId++);
            productEntity.setActive(true);
            addName(productEntity, productNameEntity("cucumber", "en"));
            addName(productEntity, productNameEntity("огурец", "ru"));

            return productEntity;
        }

        public static ProductEntity productEntity(final List<ProductNameEntity> productNameEntities) {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setId(productEntityId++);
            productEntity.setActive(true);
            productNameEntities.forEach(productNameEntity -> addName(productEntity, productNameEntity));

            return productEntity;
        }

        public static ProductNameEntity productNameEntity(final String name, final String lang) {
            final ProductNameEntity nameEntity = new ProductNameEntity();
            nameEntity.setId(productEntityNameId++);
            nameEntity.setName(name);
            nameEntity.setLang(languageEntity(lang));

            return nameEntity;
        }

        public static LanguageEntity languageEntity(final String code) {
            final LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setId(languageEntityNameId++);
            languageEntity.setCode(code);

            return languageEntity;
        }

        public static List<RecipeNameEntity> recipeNameEntities() {
            final List<RecipeNameEntity> recipeNameEntities = new ArrayList<>();
            recipeNameEntities.add(recipeNameEntity("salad from cucumber and apple", "cut cucumbers and apples", "en"));
            recipeNameEntities.add(recipeNameEntity("салат из огурца и яблока", "порежте огурцы и помидоры", "ru"));

            return recipeNameEntities;
        }

        public static RecipeNameEntity recipeNameEntity(final String name, final String description, final String lang) {
            final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
            recipeNameEntity.setId(recipeEntityNameId++);
            recipeNameEntity.setLang(languageEntity(lang));
            recipeNameEntity.setName(name);
            recipeNameEntity.setDescription(description);

            return recipeNameEntity;
        }

        public static RecipeEntity recipeEntity() {
            final RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setId(1L);
            addName(recipeEntity, recipeNameEntity("salad from cucumber and apple", "cut cucumbers and apples", "en"));
            addName(recipeEntity, recipeNameEntity("салат из огурца и яблока", "порежте огурцы и помидоры", "ru"));
            recipeEntity.setActive(true);
            recipeEntity.setType("SALAD");
            addProduct(recipeEntity, productEntity(Arrays.asList(
                    productNameEntity("cucumber", "en"),
                    productNameEntity("огурец", "ru"))));
            addProduct(recipeEntity, productEntity(Arrays.asList(
                    productNameEntity("apple", "en"),
                    productNameEntity("яблоко", "ru"))));

            return recipeEntity;
        }

        public static RecipeEntity updatedRecipeEntity() {
            final RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setId(1L);
            addName(recipeEntity, recipeNameEntity("pasta from dough and eggs", "slap the dough and crush the eggs", "en"));
            recipeEntity.setActive(true);
            recipeEntity.setType("PASTA");
            addProduct(recipeEntity, productEntity(Collections.singletonList(
                    productNameEntity("cucumber", "en"))));
            addProduct(recipeEntity, productEntity(Collections.singletonList(
                    productNameEntity("apple", "en"))));

            return recipeEntity;
        }

        private static void addProduct(final RecipeEntity recipeEntity, final ProductEntity productEntity) {
            recipeEntity.getProductEntities().add(productEntity);
            productEntity.getRecipeEntities().add(recipeEntity);
        }

        private static void addName(final RecipeEntity recipeEntity, final RecipeNameEntity recipeName) {
            recipeEntity.getRecipeNames().add(recipeName);
            recipeName.setRecipe(recipeEntity);
        }

        private static void addName(final ProductEntity productEntity, final ProductNameEntity productNameEntity) {
            productEntity.getNameEntity().add(productNameEntity);
            productNameEntity.setProduct(productEntity);
        }
    }

    public static class Dto {

        private static Long productDtoId = 1L;
        private static Long recipeDtoId = 1L;

        public static ProductToSaveDto productToSaveDto(final String name, final String lang) {
            final ProductToSaveDto dto = new ProductToSaveDto();
            dto.setId(productDtoId++);
            dto.setName(name);
            dto.setLang(lang);
            dto.setActive(true);
            return dto;
        }

        public static RecipeToSaveDto recipeToSaveDtoEN() {
            final RecipeToSaveDto recipeToSaveDto = new RecipeToSaveDto();
            recipeToSaveDto.setId(recipeDtoId++);
            recipeToSaveDto.setName("salad from cucumber and apple");
            recipeToSaveDto.setDescription("cut cucumbers and apples");
            recipeToSaveDto.setLang("en");
            recipeToSaveDto.setType("SALAD");
            recipeToSaveDto.setActive(true);
            recipeToSaveDto.getProductList().add(productToSaveDto("cucumber", "en"));
            recipeToSaveDto.getProductList().add(productToSaveDto("apple", "en"));

            return recipeToSaveDto;
        }

        public static RecipeToSaveDto recipeToUpdateDtoEN() {
            final RecipeToSaveDto recipeToSaveDto = new RecipeToSaveDto();
            recipeToSaveDto.setId(recipeDtoId++);
            recipeToSaveDto.setName("pasta from dough and eggs");
            recipeToSaveDto.setDescription("slap the dough and crush the eggs");
            recipeToSaveDto.setLang("en");
            recipeToSaveDto.setType("PASTA");
            recipeToSaveDto.setActive(true);
            recipeToSaveDto.getProductList().add(productToSaveDto("dough", "en"));
            recipeToSaveDto.getProductList().add(productToSaveDto("eggs", "en"));

            return recipeToSaveDto;
        }

        public static RecipeToSaveDto recipeToSaveDtoRU() {
            final RecipeToSaveDto recipeToSaveDto = new RecipeToSaveDto();
            recipeToSaveDto.setId(recipeDtoId++);
            recipeToSaveDto.setName("салат из огурца и яблока");
            recipeToSaveDto.setDescription("порежте огурцы и помидоры");
            recipeToSaveDto.setLang("ru");
            recipeToSaveDto.setType("SALAD");
            recipeToSaveDto.setActive(true);
            recipeToSaveDto.getProductList().add(productToSaveDto("огурец", "ru"));
            recipeToSaveDto.getProductList().add(productToSaveDto("яблоко", "ru"));

            return recipeToSaveDto;
        }

    }


}
