package com.zhdanovich.fridgeater.converter;


import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.ProductNameEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ProductConverterTest {

    private final ProductConverter converter = new ProductConverter();

    @Test
    public void productToEntityTest() {
        final ProductToSaveDto dto = MockData.Dto.productToSaveDto("cucumber", "en");

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
        final List<ProductToSaveDto> dtoList = converter.productEntityToDtoList(productEntity);

        Assert.assertEquals(productEntity.getNameEntity().size(), dtoList.size());
        int countOfMatches = 0;
        for (final ProductToSaveDto productToSaveDto : dtoList) {
            Assert.assertEquals(productToSaveDto.isActive(), productEntity.isActive());

            for (final ProductNameEntity productNameEntity : productEntity.getNameEntity()) {
                if (StringUtils.equalsIgnoreCase(productNameEntity.getName(), productToSaveDto.getName())
                        && StringUtils.equalsIgnoreCase(productNameEntity.getLang().getCode(), productToSaveDto.getLang())) {
                    countOfMatches++;
                }
            }
        }
        Assert.assertEquals(countOfMatches, productEntity.getNameEntity().size());
    }

}
