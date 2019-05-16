package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final LanguageService languageService;

    @Override
    public ProductToSaveDto addProduct(final ProductToSaveDto productToSaveDTO) {
        final LanguageEntity lang = languageService.getLanguage(productToSaveDTO.getLang());
        final ProductEntity productEntity = productConverter.productToEntity(productToSaveDTO, lang);
        final ProductEntity entity = productRepository.save(productEntity);
        productToSaveDTO.setId(entity.getId());

        return productToSaveDTO;
    }

    @Override
    public AllProductsDto getAllProducts() {
        final List<ProductEntity> allProducts = productRepository.findAll();
        final AllProductsDto allProductsDTO = new AllProductsDto();

        for (final ProductEntity allProduct : allProducts) {
            allProductsDTO.getAllProducts().addAll(productConverter.productEntityToDtoList(allProduct));
        }

        return allProductsDTO;
    }
}
