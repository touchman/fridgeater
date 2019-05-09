package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.db.dto.AllProductsDTO;
import com.zhdanovich.fridgeater.db.dto.ProductToSaveDTO;
import com.zhdanovich.fridgeater.db.dto.ProductsToGetDTO;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public ProductToSaveDTO saveProduct(@RequestBody final ProductToSaveDTO productToSaveDTO) {
        return productService.addProduct(productToSaveDTO);
    }

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.POST)
    public AllProductsDTO getAllProducts(@RequestBody final ProductsToGetDTO productsToGetDTO) {
        return productService.getAllProducts(productsToGetDTO);
    }
}
