package com.epam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;


public class Main {

    public static void main(String[] args) throws InterruptedException {


        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        String login = "testforselenium@mail.ru";
        String password = "Tr@t@t@";
        String reciever = "d.denni@mail.ru";
        String subject = "Subject of letter";
        String body = "There is a body of a letter!";

        driver.get("http://www.mail.ru");
        driver.findElement(By.id("mailbox__login")).sendKeys(login);
        driver.findElement(By.id("mailbox__password")).sendKeys(password);
        driver.findElement(By.id("mailbox__auth__remember__checkbox")).click();
        driver.findElement(By.id("mailbox__auth__button")).click();


        //new email
        wait.until(elementToBeClickable(By.cssSelector("span.b-toolbar__btn__text.b-toolbar__btn__text_pad")));
        driver.findElement(By.cssSelector("span.b-toolbar__btn__text.b-toolbar__btn__text_pad")).click();

        //write new email
        wait.until(elementToBeClickable(By.cssSelector("textarea.js-input.compose__labels__input")));
        driver.findElement(By.cssSelector("textarea.js-input.compose__labels__input")).sendKeys(reciever);
        driver.findElement(By.cssSelector("input.compose__header__field")).sendKeys(subject);


//        driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")).clear();
        driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")).sendKeys(body);

        //draft
        driver.findElement(By.xpath("//*[@data-group='save-more']")).click();
        driver.findElement(By.xpath("//*[@data-name='saveDraft']")).click();

        //go to drafts to verify
        TimeUnit.SECONDS.sleep(3);
        driver.findElement(By.xpath("//*[@href='/messages/drafts/']")).click();

        //check
        TimeUnit.SECONDS.sleep(3);
        List<WebElement> mailList = driver.findElements(By.className("b-datalist__item__addr"));
        //List<WebElement> mailList = driver.findElements(By.xpath("//*[contains(@data-bem, 'b-datalist__item')]"));
        String stringToCheck = mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).getText();
        System.out.println(stringToCheck);
        if (mailList.get(0).findElement(By.xpath("//div[text()='" + reciever + "']")).getText().contains(reciever)) {
            if (mailList.get(0).findElement(By.xpath("//*[text()='" + subject + "']")).getText().contains(subject)) {
                mailList.get(0).findElement(By.xpath("//div[text()='" + reciever + "']")).click();
            } else {
                System.out.print("Second condition is down");
            }
        } else {
            System.out.print("First condition is down");
        }


        //send
        TimeUnit.SECONDS.sleep(7);
        if (driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]"))).
                findElement(By.xpath("//body[contains(@id, 'tinymce')]")).getText().contains(body)) {
            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("//*[@data-name='send']")).click();
            System.out.println("The letter was sent");
        } else {
            System.out.println("Condition is fail");
        }

        //check that draft folder doesn't contain the letter
        TimeUnit.SECONDS.sleep(3);
        driver.findElement(By.xpath("//*[@href='/messages/drafts/']")).click();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(driver.findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).getText());
        if (!mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).
                getText().contains(stringToCheck)) {
            driver.findElement(By.xpath("//*[@href='/messages/sent/']")).click();
        } else System.out.println("The letter wasn't sent");


        //check that inbox folder contains the letter
        TimeUnit.SECONDS.sleep(3);
        System.out.println(driver.findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).getText());
        if (!mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).
                getText().contains(stringToCheck)) {
            driver.findElement(By.xpath("//*[@href='/messages/sent/']")).click();
            System.out.println("The letter was sent definitely!");
        }

        //logout
        TimeUnit.SECONDS.sleep(3);
        driver.findElement(By.id("PH_logoutLink")).click();
    }
}
