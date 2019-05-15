package com.zhdanovich.fridgeater.convertor;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductNameEntity;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ProductToSaveDTO> productEntityToDtoList(final ProductEntity productEntity) {
        final List<ProductToSaveDTO> productList = new ArrayList<>();
        final List<ProductNameEntity> nameEntity = productEntity.getNameEntity();

        for (final ProductNameEntity productNameEntity : nameEntity) {
            final ProductToSaveDTO dto = new ProductToSaveDTO();
            dto.setActive(productEntity.isActive());
            dto.setLang(productNameEntity.getLang().getCode());
            dto.setName(productNameEntity.getName());
            dto.setId(productNameEntity.getId());
            productList.add(dto);
        }

        return productList;
    }
}
