package com.zhdanovich.fridgeater.repository;

import com.zhdanovich.fridgeater.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
