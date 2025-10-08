package com.practice.ui.pages;

import com.practice.ui.config.ConfigFactory;
import com.practice.ui.core.BasePage;
import com.practice.ui.utils.ElementUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class FormFieldsPage extends BasePage {

	@FindBy(id = "name-input")
	private WebElement nameField;
	@FindBy(css = "input[type='password']")
	private WebElement passwordField;
	@FindBy(id = "drink2")
	private WebElement milkCheckbox;
	@FindBy(id = "drink3")
	private WebElement coffeeCheckbox;
	@FindBy(id = "color3")
	private WebElement yellowRadio;
	@FindBy(id = "automation")
	private WebElement automationSelect;
	@FindBy(id = "email")
	private WebElement emailField;
	@FindBy(id = "message")
	private WebElement messageField;
	@FindBy(xpath = "//button[@data-testid='submit-btn']")
	private WebElement submitBtn;

	public FormFieldsPage(WebDriver driver) {
		super(driver);
	}

	@Step("Открыть страницу форм с полями")
	public FormFieldsPage openFormPage() {
		open(ConfigFactory.getBrowserConfig().formPageUrl());
		return this;
	}

	@Step("Вводим имя: {name}")
	public FormFieldsPage enterName(String name) {
		sendKeys(nameField, name);
		return this;
	}

	@Step("Вводим пароль")
	public FormFieldsPage enterPassword(String password) {
		sendKeys(passwordField, password);
		return this;
	}

	@Step("Выбираем напитки: Milk и Coffee")
	public FormFieldsPage selectDrinks() {
		click(milkCheckbox);
		click(coffeeCheckbox);
		return this;
	}

	@Step("Выбираем цвет Yellow")
	public FormFieldsPage selectYellowColor() {
		ElementUtils.scrollToElement(driver, By.id("color3"));
		ElementUtils.hoverOverElement(driver, By.id("color3"));
		click(yellowRadio);
		return this;
	}

	@Step("Выбираем {choose} в Do you like automation?")
	public FormFieldsPage selectLikeAutomation(String choose) {
		ElementUtils.selectByVisibleText(driver, By.id("automation"), choose);
		return this;
	}

	@Step("Вводим email")
	public FormFieldsPage enterEmail(String email) {
		sendKeys(emailField, email);
		return this;
	}

	@Step("Вводим сообщение")
	public FormFieldsPage enterMessage(String message) {
		sendKeys(messageField, message);
		return this;
	}

	@Step("Нажимаем Submit")
	public void submit() {
		ElementUtils.scrollToElement(driver, By.id("submit-btn"));
		ElementUtils.hoverOverElement(driver, By.id("submit-btn"));
		click(submitBtn);
	}

	@Step("Получаем текст алерта")
	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}

	@Step("Принимаем алерт")
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
}
