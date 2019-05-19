package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "language")
@Table(name = "LANGUAGE")
public class LanguageEntity extends CommonFields {

    @Column(name = "CODE", unique = true)
    private String code;

    /*@OneToMany(mappedBy = "lang", fetch = FetchType.EAGER)
    private Collection<ProductNameEntity> productNameEntityCollection;*/
}
