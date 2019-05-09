package com.zhdanovich.fridgeater.db.dto;

import lombok.Data;

@Data
public class RecipeToSaveDTO {

    private int id;
    private String type;
    private boolean active;
    private String name;
    private String lang;
}
