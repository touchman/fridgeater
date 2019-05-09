package com.zhdanovich.fridgeater.service.impl;

import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.ProductsToGetDTO;
import com.zhdanovich.fridgeater.repository.LanguageRepository;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final LanguageRepository languageRepository;
    private final ProductConverter productConverter;

    private List<LanguageEntity> allLanguages;

    @Override
    public ProductToSaveDTO addProduct(final ProductToSaveDTO productToSaveDTO) {
        final LanguageEntity lang = getLanguage(productToSaveDTO.getLang());
        final ProductEntity productEntity = productConverter.productToEntity(productToSaveDTO, lang);
        final ProductEntity entity = productRepository.save(productEntity);
        productToSaveDTO.setId(entity.getId());

        return productToSaveDTO;
    }

    @Override
    public AllProductsDTO getAllProducts(final ProductsToGetDTO productsToGetDTO) {
        final List<ProductEntity> allProducts = productRepository.findAll();
        final AllProductsDTO allProductsDTO = new AllProductsDTO();
        allProductsDTO.setAllProducts(allProducts.stream().map(productConverter::productEntityToDto).collect(Collectors.toList()));
        return allProductsDTO;
    }

    private LanguageEntity getLanguage(final String langFromDto) {
        if (allLanguages == null || allLanguages.size() == 0) {
            allLanguages = languageRepository.findAll();
        }
        LanguageEntity lang = allLanguages.stream()
                .filter(languageEntity -> langFromDto.equalsIgnoreCase(languageEntity.getCode()))
                .findAny().orElse(null);
        if (lang == null) {
            lang = new LanguageEntity();
            lang.setCode(langFromDto);
            allLanguages.add(lang);
        }
        return lang;
    }
}
