package com.practice.ui.core;

import com.practice.ui.factory.BrowserFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Базовый класс для всех тестов
 */
@ExtendWith(TestListener.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        logger.info("Запуск теста: {}", testInfo.getDisplayName());
        driver = BrowserFactory.createDriver();
    }
    
    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            logger.info("Завершение теста: {}", testInfo.getDisplayName());
            takeScreenshot("Финальный скриншот");
            driver.quit();
        }
    }
    
    /**
     * Сделать скриншот и прикрепить к отчету Allure
     * @param name название скриншота
     */
    @Attachment(value = "{name}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        logger.debug("Создание скриншота: {}", name);
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        return screenshot;
    }
    
    /**
     * Добавить информацию в отчет Allure
     * @param name название информации
     * @param content содержимое
     */
    protected void addAllureInfo(String name, String content) {
        logger.debug("Добавление информации в отчет: {}", name);
        Allure.addAttachment(name, "text/plain", content);
    }
}