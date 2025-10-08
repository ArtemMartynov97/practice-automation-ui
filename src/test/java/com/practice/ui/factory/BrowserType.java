package com.practice.ui.factory;

/**
 * Перечисление поддерживаемых типов браузеров
 */
public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE,
    SAFARI;
    
    /**
     * Получить тип браузера из строки
     * @param browserName название браузера
     * @return тип браузера
     */
    public static BrowserType fromString(String browserName) {
        for (BrowserType type : values()) {
            if (type.name().equalsIgnoreCase(browserName)) {
                return type;
            }
        }
        return CHROME;
    }
}