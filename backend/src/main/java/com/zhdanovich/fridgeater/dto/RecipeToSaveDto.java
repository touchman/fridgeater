package com.zhdanovich.fridgeater.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeToSaveDto {

    private Long id;
    private String type;
    private boolean active;
    private String name;
    private String lang;
    private List<ProductToSaveDto> productList = new ArrayList<>();
}
