package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductNameEntity;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {

    public ProductEntity productToEntity(final ProductToSaveDTO productToSaveDTO, final LanguageEntity lang) {
        final ProductNameEntity productNameEntity = new ProductNameEntity();
        productNameEntity.setName(productToSaveDTO.getName());
        productNameEntity.setLang(lang);
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setActive(productToSaveDTO.isActive());
        productEntity.addName(productNameEntity);

        return productEntity;
    }

    public ProductToSaveDTO productEntityToDto(final ProductEntity productEntity) {
        final ProductToSaveDTO dto = new ProductToSaveDTO();
        dto.setActive(productEntity.isActive());
        dto.setLang(productEntity.getNameEntity().get(0).getLang().getCode());
        dto.setName(productEntity.getNameEntity().get(0).getName());

        return dto;
    }
}
