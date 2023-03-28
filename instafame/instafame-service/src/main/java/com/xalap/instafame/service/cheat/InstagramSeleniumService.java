/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.framework.logging.HasLog;
import com.xalap.framework.thread.ThreadService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author Usov Andrey
 * @since 2020-05-22
 */
@Service
public class InstagramSeleniumService implements HasLog {

    private final SeleniumService seleniumService;
    private final ThreadService threadService;

    public InstagramSeleniumService(SeleniumService seleniumService, ThreadService threadService) {
        this.seleniumService = seleniumService;
        this.threadService = threadService;
    }

    /**
     * Попробовал я через selenium, сразу вылетает на двух факторную авторизацию
     */
    //@EventListener({ContextRefreshedEvent.class})
    public void init() {
        seleniumService.withFireFoxDriver(new Consumer<WebDriver>() {
            @Override
            public void accept(WebDriver webDriver) {
                seleniumService.navigate(webDriver, "https://www.instagram.com/accounts/login/?next=%2F&source=logged_out_half_sheet", 2000);
                String html = webDriver.getPageSource();
                //log().debug("response:" + html);
                webDriver.findElement(By.name("username")).sendKeys("boracay.life");
                webDriver.findElement(By.name("password")).sendKeys("1qaz2wsx3edc");
                threadService.sleep(2000);
                for (WebElement button : webDriver.findElements(By.tagName("button"))) {
                    if (button.getText().equals("Войти")) {
                        button.click();
                    }
                }
                threadService.sleep(5000);
                html = webDriver.getPageSource();
                log().debug("response:" + html);
            }
        });
    }
}
