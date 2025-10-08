package com.practice.ui.core;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Слушатель событий тестов для интеграции с Allure и логирования
 */
public class TestListener implements TestWatcher {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = getTestName(context);
        logger.info("Тест успешно пройден: {}", testName);
        Allure.addAttachment("Test Status", "text/plain", "PASSED");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = getTestName(context);
        logger.error("Тест не пройден: {}", testName, cause);
        Allure.addAttachment("Test Status", "text/plain", "FAILED");
        Allure.addAttachment("Exception", "text/plain", cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = getTestName(context);
        logger.warn("Тест прерван: {}", testName, cause);
        Allure.addAttachment("Test Status", "text/plain", "ABORTED");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String testName = getTestName(context);
        logger.info("Тест отключен: {}, причина: {}", testName, reason.orElse("Не указана"));
        Allure.addAttachment("Test Status", "text/plain", "DISABLED");
        reason.ifPresent(r -> Allure.addAttachment("Reason", "text/plain", r));
    }

    private String getTestName(ExtensionContext context) {
        return context.getTestMethod()
                .map(method -> method.getDeclaringClass().getSimpleName() + "." + method.getName())
                .orElse(context.getDisplayName());
    }
}