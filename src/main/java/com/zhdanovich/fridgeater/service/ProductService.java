package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.ProductsToGetDTO;

public interface ProductService {
    ProductToSaveDTO addProduct(ProductToSaveDTO productToSaveDTO);

    AllProductsDTO getAllProducts(ProductsToGetDTO productsToGetDTO);
}
