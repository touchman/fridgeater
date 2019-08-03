package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "recipe")
@Table(name = "recipe")
public class RecipeEntity extends CommonFields {

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToMany(mappedBy = "recipeEntities",
            cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ProductEntity> productEntities = new HashSet<>();

    @OneToMany(mappedBy = "recipe",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<RecipeNameEntity> recipeNames = new ArrayList<>();
}
