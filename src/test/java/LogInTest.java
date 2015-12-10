import com.epam.MailAccount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class LogInTest extends BaseMail {

    public String getStringToCheck() {
        return stringToCheck;
    }

    public void setStringToCheck(String stringToCheck) {
        this.stringToCheck = stringToCheck;
    }

    private String stringToCheck;

    public MailAccount mailAccount;

    @Test(description = "LogIn Test")
    @Parameters({"login", "password"})
    public void logInTest(String login, String password) {
        mailAccount = new MailAccount(login, password, browser);
        mailAccount.logIn();
        Assert.assertTrue(browser.getDriver().findElement(By.id("PH_logoutLink")).isDisplayed());
    }

    @Test(description = "write new mail, save to draft and check it")
    @Parameters({"receiver", "subject", "body"})
    public void writeNewMailTest(String receiver, String subject, String body) throws InterruptedException {
        mailAccount = new MailAccount(receiver, subject, body, browser);
        mailAccount.writeMail();
        List<WebElement> mailList = browser.getDriver().findElements(By.className("b-datalist__item__addr"));
        setStringToCheck(mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).
                getText());
        Assert.assertTrue(getStringToCheck().contains(subject));
    }

    @Test(description = "write new mail, save to draft and check it")
    @Parameters({"receiver", "subject", "body"})
    public void checkMailTest(String receiver, String subject, String body) throws InterruptedException {
        mailAccount = new MailAccount(receiver, subject, body, browser);
        mailAccount.checkMail();
        Assert.assertTrue(browser.getDriver().switchTo().frame(browser.getDriver().
                findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]"))).
                findElement(By.xpath("//body[contains(@id, 'tinymce')]")).getText().contains(body));
    }

    @Test(description = "send the mail and check the draft folder")
    @Parameters({"receiver", "subject", "body"})
    public void checkDraftAfterSendingTest(String receiver, String subject, String body) throws InterruptedException {
        mailAccount = new MailAccount(receiver, subject, body, browser);
        mailAccount.checkDraftsAfterSending();
        List<WebElement> mailList = browser.getDriver().findElements(By.className("b-datalist__item__addr"));
        Assert.assertTrue(!mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).
                getText().contains(getStringToCheck()));
    }

    @Test(description = "check that sent folder isn't contain the letter after sending")
    @Parameters({"receiver", "subject", "body"})
    public void checkSentAfterSendingTest(String receiver, String subject, String body) throws InterruptedException {
        mailAccount = new MailAccount(receiver, subject, body, browser);
        mailAccount.checkSentAfterSending();
        List<WebElement> mailList = browser.getDriver().findElements(By.className("b-datalist__item__addr"));
        Assert.assertTrue(!mailList.get(0).findElement(By.xpath("//*[contains(@data-subject, '" + subject + "')]")).
                getText().contains(getStringToCheck()));
    }

    @Test(description = "check that sent folder isn't contain the letter after sending")
    @Parameters({"receiver", "subject", "body"})
    public void logOutTest() throws InterruptedException {
        mailAccount = new MailAccount(browser);
        mailAccount.logOut();
        Assert.assertTrue(browser.getDriver().findElement(By.id("mailbox__login")).isDisplayed());
    }
}
