package com.zhdanovich.fridgeater.dto;

import lombok.Data;

@Data
public class ProductToSaveDto {

    private Long id;
    private boolean active;
    private String name;
    private String lang;
}
