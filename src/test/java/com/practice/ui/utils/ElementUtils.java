package com.practice.ui.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилитный класс для работы с элементами страницы
 */
public class ElementUtils {
    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    private ElementUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Безопасный клик по элементу с предварительным ожиданием кликабельности
     * @param driver драйвер браузера
     * @param locator локатор элемента
     */
    @Step("Клик по элементу: {locator}")
    public static void click(WebDriver driver, By locator) {
        logger.debug("Клик по элементу: {}", locator);
        WebElement element = WaitUtils.waitForClickability(driver, locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.warn("Элемент перехвачен, пробуем клик через JavaScript: {}", locator);
            clickWithJS(driver, element);
        }
    }

    /**
     * Клик по элементу с использованием JavaScript
     * @param driver драйвер браузера
     * @param element элемент
     */
    @Step("Клик по элементу через JavaScript")
    public static void clickWithJS(WebDriver driver, WebElement element) {
        logger.debug("Клик по элементу через JavaScript");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Ввод текста в элемент с предварительной очисткой
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @param text текст для ввода
     */
    @Step("Ввод текста '{text}' в элемент: {locator}")
    public static void sendKeys(WebDriver driver, By locator, String text) {
        logger.debug("Ввод текста '{}' в элемент: {}", text, locator);
        WebElement element = WaitUtils.waitForVisibility(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Получение текста элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return текст элемента
     */
    @Step("Получение текста элемента: {locator}")
    public static String getText(WebDriver driver, By locator) {
        logger.debug("Получение текста элемента: {}", locator);
        return WaitUtils.waitForVisibility(driver, locator).getText();
    }

    /**
     * Проверка видимости элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @return true если элемент виден
     */
    @Step("Проверка видимости элемента: {locator}")
    public static boolean isElementVisible(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            logger.debug("Элемент не найден или устарел: {}", locator);
            return false;
        }
    }

    /**
     * Выбор опции из выпадающего списка по видимому тексту
     * @param driver драйвер браузера
     * @param locator локатор элемента select
     * @param visibleText видимый текст опции
     */
    @Step("Выбор опции '{visibleText}' из выпадающего списка: {locator}")
    public static void selectByVisibleText(WebDriver driver, By locator, String visibleText) {
        logger.debug("Выбор опции '{}' из выпадающего списка: {}", visibleText, locator);
        WebElement element = WaitUtils.waitForVisibility(driver, locator);
        new Select(element).selectByVisibleText(visibleText);
    }

    /**
     * Наведение курсора на элемент
     * @param driver драйвер браузера
     * @param locator локатор элемента
     */
    @Step("Наведение курсора на элемент: {locator}")
    public static void hoverOverElement(WebDriver driver, By locator) {
        logger.debug("Наведение курсора на элемент: {}", locator);
        WebElement element = WaitUtils.waitForVisibility(driver, locator);
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Прокрутка к элементу
     * @param driver драйвер браузера
     * @param locator локатор элемента
     */
    @Step("Прокрутка к элементу: {locator}")
    public static void scrollToElement(WebDriver driver, By locator) {
        logger.debug("Прокрутка к элементу: {}", locator);
        WebElement element = WaitUtils.waitForPresence(driver, locator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Получение списка текстов элементов
     * @param driver драйвер браузера
     * @param locator локатор элементов
     * @return список текстов
     */
    @Step("Получение списка текстов элементов: {locator}")
    public static List<String> getElementsTexts(WebDriver driver, By locator) {
        logger.debug("Получение списка текстов элементов: {}", locator);
        return driver.findElements(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Проверка наличия атрибута у элемента
     * @param driver драйвер браузера
     * @param locator локатор элемента
     * @param attribute название атрибута
     * @return значение атрибута или null если атрибут отсутствует
     */
    @Step("Получение значения атрибута '{attribute}' элемента: {locator}")
    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        logger.debug("Получение значения атрибута '{}' элемента: {}", attribute, locator);
        return WaitUtils.waitForPresence(driver, locator).getAttribute(attribute);
    }
}