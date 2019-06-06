package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "RECIPE_NAME", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LANG_ID", "NAME"})
})
public class RecipeNameEntity extends CommonFields {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "LANG_ID", nullable = false, updatable = false)
    private LanguageEntity lang;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
