package com.zhdanovich.fridgeater.db.dto;

import lombok.Data;

@Data
public class ProductToSaveDTO {

    private int id;
    private boolean active;
    private String name;
    private String lang;
}
