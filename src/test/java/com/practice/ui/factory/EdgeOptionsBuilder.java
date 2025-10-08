package com.practice.ui.factory;

import com.practice.ui.config.BrowserConfig;
import com.practice.ui.config.ConfigFactory;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Билдер для настройки EdgeOptions
 */
public class EdgeOptionsBuilder {
    private final EdgeOptions options;
    private final BrowserConfig config;
    
    public EdgeOptionsBuilder() {
        this.options = new EdgeOptions();
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
    public EdgeOptionsBuilder withArgument(String argument) {
        options.addArguments(argument);
        return this;
    }
    
    /**
     * Добавить экспериментальную опцию
     * @param name имя опции
     * @param value значение опции
     * @return текущий билдер
     */
    public EdgeOptionsBuilder withExperimentalOption(String name, Object value) {
        options.setExperimentalOption(name, value);
        return this;
    }
    
    /**
     * Построить объект EdgeOptions
     * @return настроенный объект EdgeOptions
     */
    public EdgeOptions build() {
        return options;
    }
}