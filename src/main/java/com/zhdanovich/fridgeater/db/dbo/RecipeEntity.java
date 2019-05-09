package com.zhdanovich.fridgeater.db.dbo;

import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
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
    @Enumerated(EnumType.STRING)
    private RecipeTypeEnum type;

    @Column(name = "ACTIVE")
    private boolean active;
}
