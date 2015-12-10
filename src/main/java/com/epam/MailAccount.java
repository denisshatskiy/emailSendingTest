package com.epam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailAccount {
    private String login;
    private String password;
    private String receiver;
    private String subject;
    private String body;
    Browser browser;

    public MailAccount(String login, String password, Browser browser) {
        this.login = login;
        this.password = password;
        this.browser = browser;
    }

    public MailAccount(String receiver, String subject, String body, Browser browser) {
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        this.browser = browser;
    }

    public MailAccount(Browser browser){
        this.browser = browser;
    }


    public void logIn() {
        browser.getDriver().findElement(By.id("mailbox__login")).sendKeys(login);
        browser.getDriver().findElement(By.id("mailbox__password")).sendKeys(password);
        browser.getDriver().findElement(By.id("mailbox__auth__remember__checkbox")).click();
        browser.getDriver().findElement(By.id("mailbox__auth__button")).click();
    }

    public void writeMail() throws InterruptedException {
        browser.getDriver().switchTo().defaultContent();
        browser.getDriver().findElement(By.cssSelector("span.b-toolbar__btn__text.b-toolbar__btn__text_pad")).click();
        TimeUnit.SECONDS.sleep(3);
        browser.getDriver().findElement(By.cssSelector("textarea.js-input.compose__labels__input")).sendKeys(receiver);
        browser.getDriver().findElement(By.cssSelector("input.compose__header__field")).sendKeys(subject);
        browser.getDriver().findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")).sendKeys(body);
        browser.getDriver().findElement(By.xpath("//*[@data-group='save-more']")).click();
        browser.getDriver().findElement(By.xpath("//*[@data-name='saveDraft']")).click();
        TimeUnit.SECONDS.sleep(3);
        browser.getDriver().findElement(By.xpath("//*[@href='/messages/drafts/']")).click();
        TimeUnit.SECONDS.sleep(3);
    }

    public void checkMail() throws InterruptedException {
        List<WebElement> mailList = browser.getDriver().findElements(By.className("b-datalist__item__addr"));
        mailList.get(0).findElement(By.xpath("//div[text()='" + receiver + "']")).click();
        TimeUnit.SECONDS.sleep(7);
    }

    public void checkDraftsAfterSending() throws InterruptedException {
        browser.getDriver().switchTo().defaultContent();
        browser.getDriver().findElement(By.xpath("//*[@data-name='send']")).click();
        TimeUnit.SECONDS.sleep(3);
        browser.getDriver().findElement(By.xpath("//*[@href='/messages/drafts/']")).click();
        TimeUnit.SECONDS.sleep(3);
    }

    public void checkSentAfterSending() throws InterruptedException {
        browser.getDriver().findElement(By.xpath("//*[@href='/messages/sent/']")).click();
        TimeUnit.SECONDS.sleep(3);
    }

    public void logOut() throws InterruptedException {
        browser.getDriver().findElement(By.id("PH_logoutLink")).click();
    }
}
