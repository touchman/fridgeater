package com.zhdanovich.fridgeater.controller;

import com.zhdanovich.fridgeater.dto.ProductToSaveDto;
import com.zhdanovich.fridgeater.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity saveProduct(@RequestBody final ProductToSaveDto productToSaveDto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addProduct(productToSaveDto));
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }
}
