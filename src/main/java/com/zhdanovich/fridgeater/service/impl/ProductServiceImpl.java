package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.helper.LanguageHelper;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final LanguageHelper languageHelper;

    @Override
    public ProductToSaveDTO addProduct(final ProductToSaveDTO productToSaveDTO) {
        final LanguageEntity lang = languageHelper.getLanguage(productToSaveDTO.getLang());
        final ProductEntity productEntity = productConverter.productToEntity(productToSaveDTO, lang);
        final ProductEntity entity = productRepository.save(productEntity);
        productToSaveDTO.setId(entity.getId());

        return productToSaveDTO;
    }

    @Override
    public AllProductsDTO getAllProducts() {
        final List<ProductEntity> allProducts = productRepository.findAll();
        final AllProductsDTO allProductsDTO = new AllProductsDTO();

        for (final ProductEntity allProduct : allProducts) {
            allProductsDTO.getAllProducts().addAll(productConverter.productEntityToDtoList(allProduct));
        }

        return allProductsDTO;
    }
}
