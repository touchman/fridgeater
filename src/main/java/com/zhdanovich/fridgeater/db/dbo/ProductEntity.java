package com.zhdanovich.fridgeater.db.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private int id;

    @Column(name = "ACTIVE")
    private boolean active;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<ProductNameEntity> nameEntity = new ArrayList<>();

    public void addName(final ProductNameEntity productNameEntity) {
        nameEntity.add(productNameEntity);
        productNameEntity.setProduct(this);
    }

    public void removeName(final ProductNameEntity productNameEntity) {
        nameEntity.remove(productNameEntity);
        productNameEntity.setProduct(null);
    }
}
