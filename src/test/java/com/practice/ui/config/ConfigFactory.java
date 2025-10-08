package com.practice.ui.config;

import org.aeonbits.owner.ConfigCache;

/**
 * Фабрика для получения конфигурационных объектов
 */
public class ConfigFactory {
    
    /**
     * Получить конфигурацию браузера
     * @return объект конфигурации браузера
     */
    public static BrowserConfig getBrowserConfig() {
        return ConfigCache.getOrCreate(BrowserConfig.class);
    }
}