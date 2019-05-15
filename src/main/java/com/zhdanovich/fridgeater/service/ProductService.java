package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;

public interface ProductService {
    ProductToSaveDTO addProduct(ProductToSaveDTO productToSaveDTO);

    AllProductsDTO getAllProducts();
}
