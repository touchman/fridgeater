package com.zhdanovich.fridgeater.db.dbo;

import com.zhdanovich.fridgeater.db.dbo.data.RecipeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "recipeEntities",
            cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ProductEntity> productEntities = new HashSet<>();

    @OneToMany(mappedBy = "recipe",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<RecipeNameEntity> recipeNames = new ArrayList<>();

    public void addProduct(final ProductEntity productEntity) {
        productEntities.add(productEntity);
        productEntity.getRecipeEntities().add(this);
    }

    public void removeProduct(final ProductEntity productEntity) {
        productEntities.remove(productEntity);
        productEntity.getRecipeEntities().remove(this);
    }

    public void addName(final RecipeNameEntity recipeName) {
        recipeNames.add(recipeName);
        recipeName.setRecipe(this);
    }

    public void removeName(final RecipeNameEntity recipeName) {
        recipeNames.remove(recipeName);
        recipeName.setRecipe(null);
    }
}
