package com.practice.ui.factory;

import com.practice.ui.config.BrowserConfig;
import com.practice.ui.config.ConfigFactory;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Билдер для настройки SafariOptions
 */
public class SafariOptionsBuilder {
    private final SafariOptions options;
    private final BrowserConfig config;
    
    public SafariOptionsBuilder() {
        this.options = new SafariOptions();
        this.config = ConfigFactory.getBrowserConfig();
        setupDefaultOptions();
    }
    
    /**
     * Настройка опций по умолчанию
     */
    private void setupDefaultOptions() {
        // Safari не поддерживает headless режим, поэтому игнорируем эту настройку
        
        // Автоматическое заполнение форм
        options.setAutomaticInspection(false);
        options.setAutomaticProfiling(false);
    }
    
    /**
     * Добавить возможность
     * @param capability имя возможности
     * @param value значение возможности
     * @return текущий билдер
     */
    public SafariOptionsBuilder withCapability(String capability, Object value) {
        options.setCapability(capability, value);
        return this;
    }
    
    /**
     * Построить объект SafariOptions
     * @return настроенный объект SafariOptions
     */
    public SafariOptions build() {
        return options;
    }
}