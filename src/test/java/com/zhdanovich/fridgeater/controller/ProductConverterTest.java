package com.zhdanovich.fridgeater.controller;


import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import org.junit.Assert;
import org.junit.Test;


public class ProductConverterTest {

    private final ProductConverter converter = new ProductConverter();

    @Test
    public void productToEntityTest() {
        final ProductToSaveDTO dto = new ProductToSaveDTO();
        dto.setName("cucumber");
        dto.setLang("en");
        dto.setActive(true);

        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode(dto.getLang());

        final ProductEntity productEntity = converter.productToEntity(dto, languageEntity);

        Assert.assertEquals(productEntity.getNameEntity().get(0).getName(), dto.getName());
        Assert.assertEquals(productEntity.isActive(), dto.isActive());
        Assert.assertEquals(productEntity.getNameEntity().get(0).getLang().getCode(), dto.getLang());
    }

    @Test
    public void productEntityToDto() {
        final ProductEntity productEntity = MockData.productEntity();
        final ProductToSaveDTO dto = converter.productEntityToDto(MockData.productEntity());

        Assert.assertEquals(dto.getLang(), productEntity.getNameEntity().get(0).getLang().getCode());
        Assert.assertEquals(dto.getName(), productEntity.getNameEntity().get(0).getName());
        Assert.assertTrue(dto.isActive());
    }

}
