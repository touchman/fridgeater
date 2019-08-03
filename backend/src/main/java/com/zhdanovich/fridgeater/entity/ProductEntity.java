package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

    @ManyToMany
    @JoinTable(
            name = "RECIPE_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPE_ID", referencedColumnName = "ID"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<RecipeEntity> recipeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductNameEntity> nameEntity = new ArrayList<>();
}
