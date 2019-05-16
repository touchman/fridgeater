package com.zhdanovich.fridgeater.converter;

import com.zhdanovich.fridgeater.dto.AllProductsDto;
import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
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
    public ProductToSaveDto saveProduct(@RequestBody final ProductToSaveDto productToSaveDTO) {
        return productService.addProduct(productToSaveDTO);
    }

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.POST)
    public AllProductsDto getAllProducts() {
        return productService.getAllProducts();
    }
}
