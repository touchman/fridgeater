package com.zhdanovich.fridgeater.db.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllProductsDTO {
    private List<ProductToSaveDTO> allProducts = new ArrayList<>();
}
