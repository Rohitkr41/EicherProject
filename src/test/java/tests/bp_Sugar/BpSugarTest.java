package tests.bp_Sugar;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.bp_Sugar.BpSugarPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class BpSugarTest extends BaseTest {

    @Test
    public void verifyBpSugarSearchAndClickIcon() {

        // ✅ Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ✅ Sidebar navigation
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("BP & Sugar");

        // ✅ BP & Sugar Page
        BpSugarPage bpSugar = new BpSugarPage(driver);
        bpSugar.openBpSugarPage();

//        // ✅ Perform search
        bpSugar.searchByDate("02/04/2026","03/04/2026");
      // ✅ Click icon for "New" status row
        bpSugar.clickNewStatusIcon();       
        bpSugar.clickNewAfterIconExa();
        
        bpSugar.selectBpChecked("Yes");

        bpSugar.fillBpSugarExamination(
        	    "80",              // Diastolic
        	    "120",             // Systolic
        	    "72",              // Pulse
        	    "Abnormal",          // BP Status
        	    "6",        // Duration
        	    "Weeks",          // Period
        	    "Yes",             // Use of Medicine
        	    "Patient stable, continue monitoring." // Remarks
        	);
        
       
        //sugar section data Enter
        
        bpSugar.selectSugarChecked("Yes");
        bpSugar.fillSugarExamination(
        	    "80",              // Random Sugar (mg/dL)
        	    "Abnormal",        // Sugar Status
        	    "6",               // Duration
        	    "Weeks",           // Period
        	    "Yes",             // Use of Medicine
        	    "Patient stable, continue monitoring." // Remarks
        	);
        
        bpSugar.selectReferralRequired("Yes");
        
        bpSugar.enterRemarks("Patient stable");

        bpSugar.selectReferralRequired("Yes");

        // Option 1: Save
        bpSugar.clickSave();
        bpSugar.handleSuccessPopup();

        // Option 2: Direct Refer
        // bpSugar.clickReferForClinicalExamination();
   
        
   
    }
   
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
