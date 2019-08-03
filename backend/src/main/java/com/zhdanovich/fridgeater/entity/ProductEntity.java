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
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "product")
@Table(name = "product")
public class ProductEntity extends CommonFields {

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToMany(mappedBy = "productEntities")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<RecipeEntity> recipeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductNameEntity> nameEntity = new ArrayList<>();
}
