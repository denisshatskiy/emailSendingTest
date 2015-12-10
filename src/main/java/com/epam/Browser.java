package com.epam;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {
    private WebDriver driver;
    private String url;

    public WebDriver getDriver() {
        return driver;
    }

    public Browser(String url) {
        this.url = url;
        this.driver = new FirefoxDriver();
    }

    public void OpenUrl() {
        driver.navigate().to(url);
    }
}
