package tests.camp;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.camp.CampRegistrationPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class CampRegistrationTest extends BaseTest {

    @Test
    public void verifyNewRegistrationFlow() {

        // ✅ Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ✅ Sidebar navigation
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Camp Registration");

        // ✅ Camp Registration Page
        CampRegistrationPage camp = new CampRegistrationPage(driver);

        camp.goToNewRegistration();

        // ✅ Camp select + popup handle
        camp.selectCamp("JLR-EM3-26-C19 (FROM V3M TEAM - 02/04/2026)");

        // ✅ Fill patient details in sequence
        camp.enterFirstName("Rahul");
        camp.enterLastName("Sharma");
        camp.selectGender("Male");
        camp.enterAgeYear("28");
        camp.enterAgeMonth("6");
        camp.enterNextOfKin("Amit Sharma");
        camp.enterContactNumber("9876543210");
        camp.enterDoorNo("palam");
        camp.selectDistrict("Gurugram");
        camp.selectQualification("GRADUATE");
        camp.selectOccupation("DRIVER");
        camp.enterdrivingLicense("DL384753545");
        camp.enterDrivingExp("4");
        camp.enterRemarks("Eye Checkup");
        
        camp.selectPrevEye(true);              // Yes
        camp.selectEyeExamCenter("EMV");       // EMV center
        camp.enterEyeExamDate("02/04/2024");   // Date

        camp.selectPrevEar(true);              // Yes
        camp.selectEarExamCenter("EMV");    // Others center
        camp.enterEarExamDate("02/04/2024");   // Date


        // ✅ Submit form
        
//        camp.submitForm();

        // ✅ Assertion: URL + optional success message
        Assert.assertTrue(
                driver.getCurrentUrl().contains("PatientRegistration_Camp"),
                "❌ Navigation failed"
        );

        // Agar success message ka locator available hai toh verify karo
        // Example:
        // WebElement successMsg = driver.findElement(By.xpath("//div[contains(text(),'Registration Successful')]"));
        // Assert.assertTrue(successMsg.isDisplayed(), "❌ Registration not successful");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}