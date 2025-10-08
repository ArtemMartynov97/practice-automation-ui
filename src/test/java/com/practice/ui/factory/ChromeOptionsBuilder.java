package com.practice.ui.factory;

import com.practice.ui.config.BrowserConfig;
import com.practice.ui.config.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Билдер для настройки ChromeOptions
 */
public class ChromeOptionsBuilder {
    private final ChromeOptions options;
    private final BrowserConfig config;
    
    public ChromeOptionsBuilder() {
        this.options = new ChromeOptions();
        this.config = ConfigFactory.getBrowserConfig();
        setupDefaultOptions();
    }
    
    /**
     * Настройка опций по умолчанию
     */
    private void setupDefaultOptions() {
        // Настройка режима headless
        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        
        // Общие настройки для стабильности
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        
        // Настройка размера окна
        options.addArguments("--window-size=" + config.browserWidth() + "," + config.browserHeight());
        
        // Отключение уведомлений
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
    }
    
    /**
     * Добавить аргумент командной строки
     * @param argument аргумент
     * @return текущий билдер
     */
    public ChromeOptionsBuilder withArgument(String argument) {
        options.addArguments(argument);
        return this;
    }
    
    /**
     * Добавить экспериментальную опцию
     * @param name имя опции
     * @param value значение опции
     * @return текущий билдер
     */
    public ChromeOptionsBuilder withExperimentalOption(String name, Object value) {
        options.setExperimentalOption(name, value);
        return this;
    }
    
    /**
     * Построить объект ChromeOptions
     * @return настроенный объект ChromeOptions
     */
    public ChromeOptions build() {
        return options;
    }
}