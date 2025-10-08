package com.practice.ui.core;

import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс для всех страниц
 */
public abstract class BasePage {
	protected final WebDriver driver;
	protected final WebDriverWait wait;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		logger.debug("Инициализирована страница: {}", getClass().getSimpleName());
	}

	/**
	 * Открыть URL
	 *
	 * @param url URL для открытия
	 */
	@Step("Открыть URL: {url}")
	public void open(String url) {
		logger.info("Открытие URL: {}", url);
		driver.get(url);
	}

	/**
	 * Получить текущий URL
	 *
	 * @return текущий URL
	 */
	@Step("Получить текущий URL")
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Получить заголовок страницы
	 *
	 * @return заголовок страницы
	 */
	@Step("Получить заголовок страницы")
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Ожидание видимости элемента
	 *
	 * @param element элемент для ожидания
	 * @return видимый элемент
	 */
	@Step("Ожидание видимости элемента")
	protected WebElement waitForVisibility(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Ожидание видимости элемента по локатору
	 *
	 * @param locator локатор элемента
	 * @return видимый элемент
	 */
	@Step("Ожидание видимости элемента по локатору")
	protected WebElement waitForVisibility(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Ожидание кликабельности элемента
	 *
	 * @param element элемент для ожидания
	 * @return кликабельный элемент
	 */
	@Step("Ожидание кликабельности элемента")
	protected WebElement waitForClickability(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Ожидание кликабельности элемента по локатору
	 *
	 * @param locator локатор элемента
	 * @return кликабельный элемент
	 */
	@Step("Ожидание кликабельности элемента по локатору")
	protected WebElement waitForClickability(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * Клик по элементу с ожиданием кликабельности
	 *
	 * @param element элемент для клика
	 */
	@Step("Клик по элементу")
	protected void click(WebElement element) {
		logger.debug("Клик по элементу: {}", element);
		waitForClickability(element).click();
	}

	/**
	 * Клик по элементу с локатором с ожиданием кликабельности
	 *
	 * @param locator локатор элемента для клика
	 */
	@Step("Клик по элементу с локатором")
	protected void click(By locator) {
		logger.debug("Клик по элементу с локатором: {}", locator);
		waitForClickability(locator).click();
	}

	/**
	 * Ввод текста в элемент с предварительной очисткой
	 *
	 * @param element элемент для ввода
	 * @param text    текст для ввода
	 */
	@Step("Ввод текста: {text}")
	protected void sendKeys(WebElement element, String text) {
		logger.debug("Ввод текста '{}' в элемент: {}", text, element);
		WebElement visibleElement = waitForVisibility(element);
		visibleElement.clear();
		visibleElement.sendKeys(text);
	}

	/**
	 * Ввод текста в элемент с локатором с предварительной очисткой
	 *
	 * @param locator локатор элемента для ввода
	 * @param text    текст для ввода
	 */
	@Step("Ввод текста: {text}")
	protected void sendKeys(By locator, String text) {
		logger.debug("Ввод текста '{}' в элемент с локатором: {}", text, locator);
		WebElement visibleElement = waitForVisibility(locator);
		visibleElement.clear();
		visibleElement.sendKeys(text);
	}

	/**
	 * Получить текст элемента
	 *
	 * @param element элемент
	 * @return текст элемента
	 */
	@Step("Получить текст элемента")
	protected String getText(WebElement element) {
		return waitForVisibility(element).getText();
	}

	/**
	 * Получить текст элемента с локатором
	 *
	 * @param locator локатор элемента
	 * @return текст элемента
	 */
	@Step("Получить текст элемента с локатором")
	protected String getText(By locator) {
		return waitForVisibility(locator).getText();
	}

	/**
	 * Проверить видимость элемента
	 *
	 * @param element элемент для проверки
	 * @return true, если элемент видим
	 */
	@Step("Проверить видимость элемента")
	protected boolean isElementVisible(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Проверить видимость элемента с локатором
	 *
	 * @param locator локатор элемента для проверки
	 * @return true, если элемент видим
	 */
	@Step("Проверить видимость элемента с локатором")
	protected boolean isElementVisible(By locator) {
		try {
			List<WebElement> elements = driver.findElements(locator);
			return !elements.isEmpty() && elements.get(0).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}