package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;

public interface ProductService {
    ProductToSaveDto addProduct(ProductToSaveDto productToSaveDTO);

    AllProductsDto getAllProducts();
}
