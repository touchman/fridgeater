package com.zhdanovich.fridgeater.db.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RECIPE_NAME", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LANG_ID", "NAME"})
})
public class RecipeNameEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
}
