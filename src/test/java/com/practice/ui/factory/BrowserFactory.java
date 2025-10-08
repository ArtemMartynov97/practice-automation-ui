package com.practice.ui.factory;

import com.practice.ui.config.BrowserConfig;
import com.practice.ui.config.ConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Фабрика для создания экземпляров WebDriver
 */
public class BrowserFactory {
    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);
    private static final BrowserConfig config = ConfigFactory.getBrowserConfig();
    
    /**
     * Создает экземпляр WebDriver на основе конфигурации
     * @return экземпляр WebDriver
     */
    public static WebDriver createDriver() {
        BrowserType browserType = BrowserType.fromString(config.browserType());
        WebDriver driver;
        
        if (config.isRemote()) {
            driver = createRemoteDriver(browserType);
        } else {
            driver = createLocalDriver(browserType);
        }
        
        driver.manage().window().setSize(new Dimension(config.browserWidth(), config.browserHeight()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.timeout()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.timeout()));
        
        return driver;
    }
    
    /**
     * Создает локальный экземпляр WebDriver
     * @param browserType тип браузера
     * @return экземпляр WebDriver
     */
    private static WebDriver createLocalDriver(BrowserType browserType) {
        logger.info("Creating local driver for browser: {}", browserType);
        
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptionsBuilder().build());
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptionsBuilder().build());
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(new EdgeOptionsBuilder().build());
            case SAFARI:
                return new SafariDriver(new SafariOptionsBuilder().build());
            default:
                logger.warn("Unknown browser type: {}. Using Chrome as default.", browserType);
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptionsBuilder().build());
        }
    }
    
    /**
     * Создает удаленный экземпляр WebDriver
     * @param browserType тип браузера
     * @return экземпляр RemoteWebDriver
     */
    private static WebDriver createRemoteDriver(BrowserType browserType) {
        logger.info("Creating remote driver for browser: {}", browserType);
        
        try {
            URL hubUrl = new URL(config.remoteUrl());
            
            switch (browserType) {
                case CHROME:
                    return new RemoteWebDriver(hubUrl, new ChromeOptionsBuilder().build());
                case FIREFOX:
                    return new RemoteWebDriver(hubUrl, new FirefoxOptionsBuilder().build());
                case EDGE:
                    return new RemoteWebDriver(hubUrl, new EdgeOptionsBuilder().build());
                case SAFARI:
                    return new RemoteWebDriver(hubUrl, new SafariOptionsBuilder().build());
                default:
                    logger.warn("Unknown browser type: {}. Using Chrome as default.", browserType);
                    return new RemoteWebDriver(hubUrl, new ChromeOptionsBuilder().build());
            }
        } catch (MalformedURLException e) {
            logger.error("Invalid remote URL: {}", config.remoteUrl(), e);
            throw new RuntimeException("Failed to create remote driver", e);
        }
    }
}