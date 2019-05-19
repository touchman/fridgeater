package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.ProductNameEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductConverter {

    public ProductEntity productToEntity(final ProductToSaveDto productToSaveDto, final LanguageEntity lang) {
        final ProductNameEntity productNameEntity = new ProductNameEntity();
        productNameEntity.setName(productToSaveDto.getName());
        productNameEntity.setLang(lang);
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setActive(productToSaveDto.isActive());
        addName(productEntity, productNameEntity);

        return productEntity;
    }

    public List<ProductToSaveDto> productEntityToDtoList(final ProductEntity productEntity) {
        final List<ProductToSaveDto> productList = new ArrayList<>();
        final List<ProductNameEntity> nameEntity = productEntity.getNameEntity();

        for (final ProductNameEntity productNameEntity : nameEntity) {
            final ProductToSaveDto dto = new ProductToSaveDto();
            dto.setActive(productEntity.isActive());
            dto.setLang(productNameEntity.getLang().getCode());
            dto.setName(productNameEntity.getName());
            dto.setId(productNameEntity.getId());
            productList.add(dto);
        }

        return productList;
    }

    private void addName(final ProductEntity productEntity, final ProductNameEntity productNameEntity) {
        productEntity.getNameEntity().add(productNameEntity);
        productNameEntity.setProduct(productEntity);
    }
}
