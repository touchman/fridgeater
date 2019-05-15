package com.zhdanovich.fridgeater.db.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeToSaveDTO {

    private int id;
    private String type;
    private boolean active;
    private String name;
    private String lang;
    private List<ProductToSaveDTO> productList = new ArrayList<>();
}
