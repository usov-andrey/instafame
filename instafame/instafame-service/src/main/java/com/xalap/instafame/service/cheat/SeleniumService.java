/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.framework.integration.lock.LockService;
import com.xalap.framework.thread.ThreadService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Усов Андрей
 * @since 02.08.2018
 */
@Service
public class SeleniumService {

    protected static final Logger log = LoggerFactory.getLogger(SeleniumService.class);

    private final Environment environment;
    private final LockService lockService;
    private final ThreadService threadService;

    public SeleniumService(Environment environment, LockService lockService, ThreadService threadService) {
        this.environment = environment;
        this.lockService = lockService;
        this.threadService = threadService;
    }

    public void withFireFoxDriver(Consumer<WebDriver> consumer) {
        synchronizedWithDriver(() -> {
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            return new FirefoxDriver(options);
        }, consumer);
    }
/*
    public void withChromeVisibleDriver(Consumer<WebDriver> consumer) {
        synchronizedWithDriver(ChromeDriver::new, consumer);
    }

    public void withChromeDriver(Consumer<WebDriver> consumer) {
        synchronizedWithDriver(() -> {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            return new ChromeDriver(options);
        }, consumer);
    }*/

    public void withFireFoxVisibleDriver(Consumer<WebDriver> consumer) {
        synchronizedWithDriver(FirefoxDriver::new, consumer);
    }

    private void withDriver(Supplier<WebDriver> driverSupplier, Consumer<WebDriver> consumer) {
        setupDriverPath();
        WebDriver driver = null;
        try {
            driver = driverSupplier.get();
            consumer.accept(driver);
        } finally {
            if (driver != null) {
                //driver.close();
                driver.quit();
            }
        }
    }

    private void synchronizedWithDriver(Supplier<WebDriver> driverSupplier, Consumer<WebDriver> consumer) {
        lockService.withLock(SeleniumService.class.getName(), new Runnable() {
            @Override
            public void run() {
                withDriver(driverSupplier, consumer);
            }
        });
    }

    public void navigate(WebDriver driver, String url, int timeout) {
        log.debug("Send request with selenium to url:" + url);
        driver.navigate().to(url);
        threadService.sleep(timeout);
    }

    public String getHtml(WebDriver driver) {
        return driver.getPageSource();
    }

    public String getJson(WebDriver driver) {
        return driver.findElement(By.tagName("body")).getText();
    }

    private void setupDriverPath() {
        System.setProperty("webdriver.gecko.driver", getFireFoxDriverPath());
        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
    }

    protected String getFireFoxDriverPath() {
        return environment.getProperty("webdriver.firefox.path");
    }

    protected String getChromeDriverPath() {
        return environment.getProperty("webdriver.chrome.path");
    }

    public void selectOption(WebDriver webDriver, By by, String value) {
        WebElement select = webDriver.findElement(by);
        Select dropDown = new Select(select);
        dropDown.selectByValue(value);
    }
}
