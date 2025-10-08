package com.practice.ui.factory;

import com.practice.ui.config.BrowserConfig;
import com.practice.ui.config.ConfigFactory;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Билдер для настройки FirefoxOptions
 */
public class FirefoxOptionsBuilder {
    private final FirefoxOptions options;
    private final BrowserConfig config;
    
    public FirefoxOptionsBuilder() {
        this.options = new FirefoxOptions();
        this.config = ConfigFactory.getBrowserConfig();
        setupDefaultOptions();
    }
    
    /**
     * Настройка опций по умолчанию
     */
    private void setupDefaultOptions() {
        // Настройка режима headless
        if (config.isHeadless()) {
            options.addArguments("-headless");
        }
        
        // Настройка профиля Firefox
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("dom.webnotifications.enabled", false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        
        options.setProfile(profile);
        
        // Общие настройки для стабильности
        options.addArguments("--width=" + config.browserWidth());
        options.addArguments("--height=" + config.browserHeight());
    }
    
    /**
     * Добавить аргумент командной строки
     * @param argument аргумент
     * @return текущий билдер
     */
    public FirefoxOptionsBuilder withArgument(String argument) {
        options.addArguments(argument);
        return this;
    }
    
    /**
     * Добавить настройку профиля
     * @param name имя настройки
     * @param value значение настройки
     * @return текущий билдер
     */
    public FirefoxOptionsBuilder withPreference(String name, Object value) {
        options.addPreference(name, value);
        return this;
    }
    
    /**
     * Построить объект FirefoxOptions
     * @return настроенный объект FirefoxOptions
     */
    public FirefoxOptions build() {
        return options;
    }
}