package com.zhdanovich.fridgeater.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "language")
@Table(name = "LANGUAGE")
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", unique = true)
    private String code;

    /*@OneToMany(mappedBy = "lang", fetch = FetchType.EAGER)
    private Collection<ProductNameEntity> productNameEntityCollection;*/
}
