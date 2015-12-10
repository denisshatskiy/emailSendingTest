import com.epam.Browser;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseMail {

    public Browser browser;

    @BeforeTest
    public void baseMail() {
        browser = new Browser("https://mail.ru");
        browser.OpenUrl();
    }

    @AfterTest
    public void closeBrowser() {
        browser.getDriver().close();
    }
}
