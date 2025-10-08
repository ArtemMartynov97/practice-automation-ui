package com.practice.ui.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.practice.ui.core.BaseTest;
import com.practice.ui.pages.FormFieldsPage;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Tag("FormPageTest")
public class FormTest extends BaseTest {

	@Test
	@DisplayName("Заполнение и отправка формы")
	public void testFormSubmission() {
		FormFieldsPage formFieldsPage = new FormFieldsPage(driver);

		formFieldsPage.openFormPage();

		List<WebElement> toolElements = driver.findElements(
				By.xpath("//label[.='Automation tools']/following-sibling::ul//li"));
		int toolsCount = toolElements.size();
		String longestTool = toolElements.stream().map(WebElement::getText)
				.max(Comparator.comparingInt(String::length)).orElse("");

		formFieldsPage.enterName("Name").enterPassword("Password").selectDrinks()
				.selectYellowColor().selectLikeAutomation("Yes").enterEmail("name@example.com")
				.enterMessage("Tools: " + toolsCount + "\nLongest: " + longestTool).submit();

		String alertText = formFieldsPage.getAlertText();
		assertEquals("Message received!", alertText);

		formFieldsPage.acceptAlert();
	}

}