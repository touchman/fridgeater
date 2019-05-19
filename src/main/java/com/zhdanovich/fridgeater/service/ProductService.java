package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.entity.ProductEntity;

public interface ProductService {
    ProductToSaveDto addProduct(ProductToSaveDto productToSaveDto);

    AllProductsDto getProducts();

    ProductEntity getProductEntityIfExist(final ProductToSaveDto productToSaveDto);
}
