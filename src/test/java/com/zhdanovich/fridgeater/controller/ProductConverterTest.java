package com.zhdanovich.fridgeater.controller;


import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductNameEntity;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ProductConverterTest {

    private final ProductConverter converter = new ProductConverter();

    @Test
    public void productToEntityTest() {
        final ProductToSaveDTO dto = MockData.Dto.productToSaveDTO();

        final LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setCode(dto.getLang());

        final ProductEntity productEntity = converter.productToEntity(dto, languageEntity);

        Assert.assertEquals(productEntity.getNameEntity().get(0).getName(), dto.getName());
        Assert.assertEquals(productEntity.isActive(), dto.isActive());
        Assert.assertEquals(productEntity.getNameEntity().get(0).getLang().getCode(), dto.getLang());
    }

    @Test
    public void productEntityToDto() {
        final ProductEntity productEntity = MockData.Entity.productEntity();
        final List<ProductToSaveDTO> dtoList = converter.productEntityToDtoList(productEntity);

        Assert.assertEquals(dtoList.size(), 2);
        int countOfMatches = 0;
        for (final ProductToSaveDTO productToSaveDTO : dtoList) {
            Assert.assertEquals(productToSaveDTO.isActive(), productEntity.isActive());

            for (final ProductNameEntity productNameEntity : productEntity.getNameEntity()) {
                if (StringUtils.equalsIgnoreCase(productNameEntity.getName(), productToSaveDTO.getName())
                        && StringUtils.equalsIgnoreCase(productNameEntity.getLang().getCode(), productToSaveDTO.getLang())) {
                    countOfMatches++;
                }
            }
        }
        Assert.assertEquals(countOfMatches, productEntity.getNameEntity().size());
    }

}
