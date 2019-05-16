package com.zhdanovich.fridgeater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@Entity(name = "productName")
@Table(name = "PRODUCT_NAME", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LANG_ID", "NAME"})
})
public class ProductNameEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "LANG_ID", nullable = false, updatable = false)
    private LanguageEntity lang;

    @Column(name = "NAME")
    private String name;
}
