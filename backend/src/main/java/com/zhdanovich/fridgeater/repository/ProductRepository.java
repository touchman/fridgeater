package com.zhdanovich.fridgeater.repository;

import com.zhdanovich.fridgeater.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM product p JOIN p.nameEntity pn on p.id = pn.product.id WHERE pn.name = :productName and pn.lang.id = :langId")
    Optional<ProductEntity> findByNameAndLang(@Param("productName") String productName,
                                              @Param("langId") Long langId);
}
