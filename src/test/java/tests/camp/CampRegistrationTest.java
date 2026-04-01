package tests.camp;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import org.testng.Assert;

import pages.LoginPage;
import pages.SidebarPage;
import pages.camp.CampRegistrationPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class CampRegistrationTest extends BaseTest {

    @Test
    public void verifyNewRegistrationFlow() {

        // Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Navigate to Camp Registration
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Camp Registration");

        // Camp Page Actions
        CampRegistrationPage camp = new CampRegistrationPage(driver);

        camp.selectCamp("Test Camp");
        camp.clickNewRegistration();

        // Validation
        Assert.assertTrue(driver.getCurrentUrl().contains("registration"),
                "Navigation to New Registration failed");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}