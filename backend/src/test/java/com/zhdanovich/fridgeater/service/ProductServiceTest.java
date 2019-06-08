package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.converter.ProductConverter;
import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.entity.ProductEntity;
import com.zhdanovich.fridgeater.repository.ProductRepository;
import com.zhdanovich.fridgeater.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LanguageService languageService;

    @Spy
    private ProductConverter productConverter;

    @Test
    public void addProductTest() {
        final ProductToSaveDto productToSaveDto = MockData.Dto.productToSaveDto("cucumber", "en");

        final ProductEntity productEntity = MockData.Entity.productEntity();
        final LanguageEntity languageEntity = MockData.Entity.languageEntity("en");

        doReturn(productEntity).when(productRepository).save(any(ProductEntity.class));
        doReturn(languageEntity).when(languageService).getLanguage(any(String.class));

        productService.addProduct(productToSaveDto);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void addProductAlreadyExistTest() {
        final ProductToSaveDto productToSaveDto = MockData.Dto.productToSaveDto("cucumber", "en");

        final ProductEntity productEntity = MockData.Entity.productEntity();
        final LanguageEntity languageEntity = MockData.Entity.languageEntity("en");

        doReturn(Optional.ofNullable(productEntity)).when(productRepository).findByNameAndLang(anyString(), anyLong());
        doReturn(languageEntity).when(languageService).getLanguage(any(String.class));

        productService.addProduct(productToSaveDto);

        verify(productRepository, times(0)).save(any(ProductEntity.class));
        verify(productRepository, times(1)).findByNameAndLang(anyString(), anyLong());
    }

    @Test
    public void getAllProductsTest() {
        final List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(MockData.Entity.productEntity());

        doReturn(productEntities).when(productRepository).findAll();

        final AllProductsDto allProductsDto = productService.getProducts();

        Assert.assertEquals(2, allProductsDto.getAllProducts().size());
        Assert.assertEquals(allProductsDto.getAllProducts().get(0).getName(), productEntities.get(0).getNameEntity().get(0).getName());
        Assert.assertEquals(allProductsDto.getAllProducts().get(0).getLang(), productEntities.get(0).getNameEntity().get(0).getLang().getCode());
    }

}
