package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.convertor.ProductConverter;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.db.dbo.ProductEntity;
import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.ProductsToGetDTO;
import com.zhdanovich.fridgeater.repository.LanguageRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private LanguageRepository languageRepository;

    @Spy
    private ProductConverter productConverter;

    @Test
    public void createProduct() {
        final ProductToSaveDTO productToSaveDTO = new ProductToSaveDTO();
        productToSaveDTO.setActive(true);
        productToSaveDTO.setLang("en");
        productToSaveDTO.setName("cucumber");

        final ProductEntity productEntity = MockData.productEntity();
        final LanguageEntity languageEntity = MockData.languageEntity();
        final List<LanguageEntity> entityList = new ArrayList<>();
        entityList.add(languageEntity);

        doReturn(productEntity).when(productRepository).save(any(ProductEntity.class));
        doReturn(entityList).when(languageRepository).findAll();

        service.addProduct(productToSaveDTO);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void getAllProductsTest() {
        final ProductsToGetDTO productsToGetDTO = new ProductsToGetDTO();
        productsToGetDTO.setLang("en");

        final List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(MockData.productEntity());

        doReturn(productEntities).when(productRepository).findAll();

        final AllProductsDTO allProductsDTO = service.getAllProducts(productsToGetDTO);

        Assert.assertEquals(1, allProductsDTO.getAllProducts().size());
        Assert.assertEquals(allProductsDTO.getAllProducts().get(0).getName(), productEntities.get(0).getNameEntity().get(0).getName());
    }

}
