package com.zhdanovich.fridgeater.entity;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
abstract class CommonFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    //@EqualsAndHashCode.Exclude
    private Long id;
}
