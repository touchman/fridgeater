package com.zhdanovich.fridgeater;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.dto.RecipeToSaveDto;
import com.zhdanovich.fridgeater.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {

    public static class Entity {
        public static ProductEntity productEntity() {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setActive(true);
            productEntity.addName(productNameEntity("cucumber", "en"));
            productEntity.addName(productNameEntity("огурец", "ru"));

            return productEntity;
        }

        public static ProductEntity productEntity(final List<ProductNameEntity> productNameEntities) {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setActive(true);
            productNameEntities.forEach(productEntity::addName);

            return productEntity;
        }

        public static ProductNameEntity productNameEntity(final String name, final String lang) {
            final ProductNameEntity nameEntity = new ProductNameEntity();
            nameEntity.setName(name);
            nameEntity.setLang(languageEntity(lang));

            return nameEntity;
        }

        public static LanguageEntity languageEntity(final String code) {
            final LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setCode(code);

            return languageEntity;
        }

        public static List<RecipeNameEntity> recipeNameEntities() {
            final List<RecipeNameEntity> recipeNameEntities = new ArrayList<>();
            recipeNameEntities.add(recipeNameEntity("salad from cucumber and apple", "en"));
            recipeNameEntities.add(recipeNameEntity("салат из огурца и яблока", "ru"));

            return recipeNameEntities;
        }

        public static RecipeNameEntity recipeNameEntity(final String name, final String lang) {
            final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
            recipeNameEntity.setLang(languageEntity(lang));
            recipeNameEntity.setName(name);

            return recipeNameEntity;
        }

        public static RecipeEntity recipeEntity() {
            final RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.addName(recipeNameEntity("salad from cucumber and apple", "en"));
            recipeEntity.addName(recipeNameEntity("салат из огурца и яблока", "ru"));
            recipeEntity.setActive(true);
            recipeEntity.setType("SALAD");
            recipeEntity.addProduct(productEntity(Arrays.asList(
                    productNameEntity("cucumber", "en"),
                    productNameEntity("огурец", "ru"))));
            recipeEntity.addProduct(productEntity(Arrays.asList(
                    productNameEntity("apple", "en"),
                    productNameEntity("яблоко", "ru"))));

            return recipeEntity;
        }
    }

    public static class Dto {

        public static ProductToSaveDto productToSaveDto(final String name, final String lang) {
            final ProductToSaveDto dto = new ProductToSaveDto();
            dto.setName(name);
            dto.setLang(lang);
            dto.setActive(true);
            return dto;
        }

        public static RecipeToSaveDto recipeToSaveDtoEN() {
            final RecipeToSaveDto recipeToSaveDTO = new RecipeToSaveDto();
            recipeToSaveDTO.setName("salad from cucumber and apple");
            recipeToSaveDTO.setLang("en");
            recipeToSaveDTO.setType("SALAD");
            recipeToSaveDTO.setActive(true);
            recipeToSaveDTO.getProductList().add(productToSaveDto("cucumber", "en"));
            recipeToSaveDTO.getProductList().add(productToSaveDto("apple", "en"));

            return recipeToSaveDTO;
        }

        public static RecipeToSaveDto recipeToSaveDtoRU() {
            final RecipeToSaveDto recipeToSaveDTO = new RecipeToSaveDto();
            recipeToSaveDTO.setName("салат из огурца и яблока");
            recipeToSaveDTO.setLang("ru");
            recipeToSaveDTO.setType("SALAD");
            recipeToSaveDTO.setActive(true);
            recipeToSaveDTO.getProductList().add(productToSaveDto("огурец", "ru"));
            recipeToSaveDTO.getProductList().add(productToSaveDto("яблоко", "ru"));

            return recipeToSaveDTO;
        }

    }


}
