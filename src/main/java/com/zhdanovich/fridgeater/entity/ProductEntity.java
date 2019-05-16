package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "product")
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

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
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<ProductNameEntity> nameEntity = new ArrayList<>();

    public void addRecipe(final RecipeEntity recipeEntity) {
        recipeEntities.add(recipeEntity);
        recipeEntity.getProductEntities().add(this);
    }

    public void removeRecipe(final RecipeEntity recipeEntity) {
        recipeEntities.remove(recipeEntity);
        recipeEntity.getProductEntities().remove(this);
    }

    public void addName(final ProductNameEntity productNameEntity) {
        nameEntity.add(productNameEntity);
        productNameEntity.setProduct(this);
    }

    public void removeName(final ProductNameEntity productNameEntity) {
        nameEntity.remove(productNameEntity);
        productNameEntity.setProduct(null);
    }
}
