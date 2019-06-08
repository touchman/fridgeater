package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.converter.ProductConverter;
import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final LanguageService languageService;

    @Override
    public ProductToSaveDto addProduct(final ProductToSaveDto productToSaveDto) {
        final LanguageEntity lang = languageService.getLanguage(productToSaveDto.getLang());
        final Optional<ProductEntity> productEntity = productRepository.findByNameAndLang(productToSaveDto.getName(), lang.getId());
        final ProductEntity entity = productEntity.orElseGet(() -> productRepository.save(productConverter.productToEntity(productToSaveDto, lang)));
        productToSaveDto.setId(entity.getId());

        return productToSaveDto;
    }

    @Override
    public AllProductsDto getProducts() {
        final AllProductsDto allProductsDto = new AllProductsDto();

        productRepository.findAll().forEach(productEntity -> allProductsDto.getAllProducts().addAll(productConverter.productEntityToDtoList(productEntity)));

        return allProductsDto;
    }
}
