package com.zhdanovich.fridgeater.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllProductsDto {
    private List<ProductToSaveDto> allProducts = new ArrayList<>();
}
