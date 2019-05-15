package com.zhdanovich.fridgeater;

import com.zhdanovich.fridgeater.db.dbo.*;
import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.RecipeToSaveDTO;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static class Entity {
        public static ProductEntity productEntity() {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.setActive(true);
            productEntity.addName(productNameEntityEN());
            productEntity.addName(productNameEntityRU());

            return productEntity;
        }

        public static List<ProductNameEntity> productNameEntities() {
            final List<ProductNameEntity> list = new ArrayList<>();
            list.add(productNameEntityEN());
            list.add(productNameEntityRU());
            return list;
        }

        public static ProductNameEntity productNameEntityEN() {
            final ProductNameEntity nameEntity = new ProductNameEntity();
            nameEntity.setName("cucumber");
            nameEntity.setLang(languageEntityEN());

            return nameEntity;
        }

        public static ProductNameEntity productNameEntityRU() {
            final ProductNameEntity nameEntity = new ProductNameEntity();
            nameEntity.setName("огурец");
            nameEntity.setLang(languageEntityRU());

            return nameEntity;
        }

        public static LanguageEntity languageEntityEN() {
            final LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setCode("en");

            return languageEntity;
        }

        public static LanguageEntity languageEntityRU() {
            final LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setCode("ru");

            return languageEntity;
        }

        public static List<RecipeNameEntity> recipeNameEntities() {
            final List<RecipeNameEntity> recipeNameEntities = new ArrayList<>();
            recipeNameEntities.add(recipeNameEntityEN());
            recipeNameEntities.add(recipeNameEntityRU());

            return recipeNameEntities;
        }

        public static RecipeNameEntity recipeNameEntityEN() {
            final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
            recipeNameEntity.setLang(languageEntityEN());
            recipeNameEntity.setName("salad from cucumbers");

            return recipeNameEntity;
        }

        public static RecipeNameEntity recipeNameEntityRU() {
            final RecipeNameEntity recipeNameEntity = new RecipeNameEntity();
            recipeNameEntity.setLang(languageEntityRU());
            recipeNameEntity.setName("салат из огурцов");

            return recipeNameEntity;
        }

        public static RecipeEntity recipeEntity() {
            final RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.addName(recipeNameEntityEN());
            recipeEntity.addName(recipeNameEntityRU());
            recipeEntity.setActive(true);
            recipeEntity.setType(RecipeTypeEnum.SALAD);
            recipeEntity.addProduct(productEntity());

            return recipeEntity;
        }
    }

    public static class Dto {

        public static ProductToSaveDTO productToSaveDTO() {
            final ProductToSaveDTO dto = new ProductToSaveDTO();
            dto.setName("cucumber");
            dto.setLang("en");
            dto.setActive(true);
            return dto;
        }

        public static RecipeToSaveDTO recipeToSaveDTO() {
            final RecipeToSaveDTO recipeToSaveDTO = new RecipeToSaveDTO();
            recipeToSaveDTO.setName("salad from cucumber");
            recipeToSaveDTO.setLang("en");
            recipeToSaveDTO.setType(RecipeTypeEnum.SALAD.name());
            recipeToSaveDTO.setActive(true);
            recipeToSaveDTO.getProductList().add(productToSaveDTO());

            return recipeToSaveDTO;
        }

    }


}
