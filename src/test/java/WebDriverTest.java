import org.junit.*;
import org.openqa.selenium.By;
import support.TestContext;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getDriver;

public class WebDriverTest {

    @BeforeClass
    public static void setup() {
        TestContext.initialize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void verifyTitle() {
        getDriver().get("https://skryabin.com/market/quote.html");
        String actualTitle = getDriver().getTitle();
        assertThat(actualTitle).isEqualTo("Get a Quote");
    }
    @Before
    public void before(){
        getDriver().manage().deleteAllCookies();
    }

    @Test
    public void verifyNewWindow() {
        getDriver().get("https://skryabin.com/market/quote.html");
        // record old set before new window opens
        Set<String> oldWindows = getDriver().getWindowHandles();
        // click button so new window opens
        getDriver().findElement(By.xpath("//button[contains(@onclick,'new')]")).click();
        // record new set after new window opens
        Set<String> newWindows = getDriver().getWindowHandles();
        // substract old from new, so only one window left
        newWindows.removeAll(oldWindows);
        // switch on only one left window handle from new set
        getDriver().switchTo().window(newWindows.iterator().next());

        String actualDocuments = getDriver().findElement(By.xpath("//ul")).getText();
        assertThat(actualDocuments).contains("Document 2");
    }

    @AfterClass
    public static void teardown() {
        getDriver().quit();
    }

}
