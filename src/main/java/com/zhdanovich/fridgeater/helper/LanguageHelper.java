package com.zhdanovich.fridgeater.helper;

import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LanguageHelper {

    private final LanguageRepository languageRepository;

    private List<LanguageEntity> allLanguages;

    public LanguageEntity getLanguage(final String langFromDto) {
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
