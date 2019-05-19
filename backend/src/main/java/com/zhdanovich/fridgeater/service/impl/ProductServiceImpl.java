package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.entity.ProductNameEntity;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.LanguageService;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final ProductEntity productEntity = getProductEntityIfExist(productToSaveDto);
        final ProductEntity entity = productEntity != null ? productEntity : productRepository.save(productConverter.productToEntity(productToSaveDto, lang));
        productToSaveDto.setId(entity.getId());

        return productToSaveDto;
    }

    @Override
    public AllProductsDto getProducts() {
        final List<ProductEntity> allProducts = productRepository.findAll();
        final AllProductsDto allProductsDto = new AllProductsDto();

        for (final ProductEntity allProduct : allProducts) {
            allProductsDto.getAllProducts().addAll(productConverter.productEntityToDtoList(allProduct));
        }

        return allProductsDto;
    }

    @Override
    public ProductEntity getProductEntityIfExist(final ProductToSaveDto productToSaveDto) {
        final List<ProductEntity> productEntities = productRepository.findAll();
        ProductEntity productEntity = null;
        for (final ProductEntity entity : productEntities) {
            final Optional<ProductNameEntity> nameEntity = entity.getNameEntity().stream().filter(productNameEntity -> productNameEntity.getLang().getCode().equalsIgnoreCase(productToSaveDto.getLang()))
                    .filter(productNameEntity -> productNameEntity.getName().equals(productToSaveDto.getName())).findAny();
            if (nameEntity.isPresent()) {
                productEntity = entity;
                break;
            }
        }
        return productEntity;
    }
}
