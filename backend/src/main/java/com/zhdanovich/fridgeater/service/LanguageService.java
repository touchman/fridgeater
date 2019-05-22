package com.zhdanovich.fridgeater.service;

import com.zhdanovich.fridgeater.entity.LanguageEntity;
import com.zhdanovich.fridgeater.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    private List<LanguageEntity> allLanguages;

    public LanguageEntity getLanguage(final String langFromDto) {
        synchronized (LanguageEntity.class) {
            if (allLanguages == null || allLanguages.size() == 0) {
                allLanguages = languageRepository.findAll();
            }
            LanguageEntity lang = allLanguages.stream()
                    .filter(languageEntity -> langFromDto.equalsIgnoreCase(languageEntity.getCode()))
                    .findAny().orElse(null);
            if (lang == null) {
                lang = new LanguageEntity();
                lang.setCode(langFromDto);
                allLanguages.add(lang);
                languageRepository.save(lang);
            }
            return lang;
        }
    }

}
