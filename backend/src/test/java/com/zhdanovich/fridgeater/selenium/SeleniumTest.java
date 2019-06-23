package com.zhdanovich.fridgeater.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    @Test
    public void test() {
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        final WebDriver driver = new ChromeDriver();

        final String baseUrl = "http://localhost";
        final String expectedTitle = "frontend";
        String actualTitle = "";

        driver.get(baseUrl);

        actualTitle = driver.getTitle();

        Assert.assertTrue(actualTitle.contentEquals(expectedTitle));

        driver.close();
    }
}
