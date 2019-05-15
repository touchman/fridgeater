package com.zhdanovich.fridgeater.helper;

import com.zhdanovich.fridgeater.MockData;
import com.zhdanovich.fridgeater.db.dbo.LanguageEntity;
import com.zhdanovich.fridgeater.repository.LanguageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LanguageHelperTest {

    @InjectMocks
    private LanguageHelper languageHelper;

    @Mock
    private LanguageRepository languageRepository;

    @Test
    public void getLanguageTest() {
        final List<LanguageEntity> languageEntities = new ArrayList<>();
        languageEntities.add(MockData.Entity.languageEntityEN());
        languageEntities.add(MockData.Entity.languageEntityRU());

        final String languageToGet = "en";

        doReturn(languageEntities).when(languageRepository).findAll();

        final LanguageEntity language = languageHelper.getLanguage(languageToGet);

        Assert.assertEquals(language.getCode(), languageToGet);
    }

    @Test
    public void getLanguageNotExistsTest() {
        final List<LanguageEntity> languageEntities = new ArrayList<>();
        languageEntities.add(MockData.Entity.languageEntityEN());
        languageEntities.add(MockData.Entity.languageEntityRU());

        final String languageToGet = "es";

        doReturn(languageEntities).when(languageRepository).findAll();

        final LanguageEntity language = languageHelper.getLanguage(languageToGet);

        Assert.assertEquals(language.getCode(), languageToGet);
    }
}
