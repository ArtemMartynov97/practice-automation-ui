package com.practice.ui.utils;

import com.practice.ui.config.ConfigFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

/**
 * Утилитный класс для работы с ожиданиями элементов
 */
public class WaitUtils {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    private static final long DEFAULT_TIMEOUT = ConfigFactory.getBrowserConfig().timeout();
    private static final long POLLING_INTERVAL = 500;

    private WaitUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Создать объект ожидания с настройками по умолчанию
     * @param driver драйвер браузера
     * @return объект ожидания
     */
    public static Wait<WebDriver> createWait(WebDriver driver) {
        return createWait(driver, DEFAULT_TIMEOUT);
    }

    /**
     * Создать объект ожидания с указанным таймаутом
     * @param driver драйвер браузера
     * @param timeoutInSeconds таймаут в секундах
     * @return объект ожидания
     */
    public static Wait<WebDriver> createWait(WebDriver driver, long timeoutInSeconds) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    /**
     * Ожидание видимости элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return найденный элемент
     */
    @Step("Ожидание видимости элемента: {locator}")
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        logger.debug("Ожидание видимости элемента: {}", locator);
        return createWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Ожидание видимости элемента с указанным таймаутом
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @param timeoutInSeconds таймаут в секундах
     * @return найденный элемент
     */
    @Step("Ожидание видимости элемента: {locator} с таймаутом {timeoutInSeconds} сек")
    public static WebElement waitForVisibility(WebDriver driver, By locator, long timeoutInSeconds) {
        logger.debug("Ожидание видимости элемента: {} с таймаутом {} сек", locator, timeoutInSeconds);
        return createWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Ожидание кликабельности элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return найденный элемент
     */
    @Step("Ожидание кликабельности элемента: {locator}")
    public static WebElement waitForClickability(WebDriver driver, By locator) {
        logger.debug("Ожидание кликабельности элемента: {}", locator);
        return createWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Ожидание присутствия элемента в DOM
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return найденный элемент
     */
    @Step("Ожидание присутствия элемента в DOM: {locator}")
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        logger.debug("Ожидание присутствия элемента в DOM: {}", locator);
        return createWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Ожидание исчезновения элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return true если элемент исчез
     */
    @Step("Ожидание исчезновения элемента: {locator}")
    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        logger.debug("Ожидание исчезновения элемента: {}", locator);
        return createWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Ожидание загрузки страницы
     * @param driver драйвер браузера
     */
    @Step("Ожидание загрузки страницы")
    public static void waitForPageLoad(WebDriver driver) {
        logger.debug("Ожидание загрузки страницы");
        createWait(driver).until(webDriver -> 
            ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Ожидание выполнения пользовательского условия
     * @param driver драйвер браузера
     * @param condition условие ожидания
     * @param <T> тип результата
     * @return результат выполнения условия
     */
    public static <T> T waitFor(WebDriver driver, Function<WebDriver, T> condition) {
        logger.debug("Ожидание выполнения пользовательского условия");
        return createWait(driver).until(condition);
    }
}