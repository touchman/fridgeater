package com.zhdanovich.fridgeater;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductNameEntity;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static ProductEntity productEntity() {
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setActive(true);
        productEntity.setNameEntity(productNameEntity());

        return productEntity;
    }

    public static List<ProductNameEntity> productNameEntity() {
        final ProductNameEntity nameEntity = new ProductNameEntity();
        nameEntity.setName("cucumber");
        nameEntity.setLang(languageEntity());

        final List<ProductNameEntity> list = new ArrayList<>();
        list.add(nameEntity);
        return list;
    }


    public static LanguageEntity languageEntity() {
        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode("en");

        return languageEntity;
    }
}
