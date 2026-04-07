//base test//


package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;   // ✅ ADD THIS

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        // implicit wait zero
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        // page load wait
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // ✅ EXPLICIT WAIT INIT (MAIN FIX)
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // open application
        driver.get(ConfigReader.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
//            driver.quit();
            driver = null;
        }
    }
}