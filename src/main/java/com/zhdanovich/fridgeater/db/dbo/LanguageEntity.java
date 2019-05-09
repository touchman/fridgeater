package com.zhdanovich.fridgeater.db.dbo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "language")
@Table(name = "LANGUAGE")
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CODE", unique = true)
    private String code;

    /*@OneToMany(mappedBy = "lang", fetch = FetchType.EAGER)
    private Collection<ProductNameEntity> productNameEntityCollection;*/
}
