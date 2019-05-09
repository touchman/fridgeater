package com.zhdanovich.fridgeater.repository;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

}
