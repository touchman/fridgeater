package com.zhdanovich.fridgeater.db.dbo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RECIPE")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ACTIVE")
    private boolean active;
}
