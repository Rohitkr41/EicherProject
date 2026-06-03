package tests.spectacleBooking;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.spectacleBooking.SpectacleBookingPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class SpectacleBookingTest extends BaseTest {

    @Test
    public void verifySpectacleBookingFlow() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                
                ConfigReader.getProperty("password")
        );


        // ===== SIDEBAR NAVIGATION =====
        SidebarPage sidebar = new SidebarPage(driver);

        sidebar.clickMenu("Spectacle Booking");


        // ===== PAGE OBJECT =====
        SpectacleBookingPage spectacle = new SpectacleBookingPage(driver);
        spectacle.searchByDate("02/05/2026","30/05/2026");
        boolean isBookingOpened =
                spectacle.clickSpectacleBookingIcon();

        if (!isBookingOpened) {

            System.out.println("✅ Test Passed - Record Already Booked");

            return;
        }

        // ===== SPECTACLE BOOKING FLOW =====
//        spectacle.fillSpectacleBookingForm(
//
//                "No",                    // Booking Status
//
//                "",                      // Spectacle Type
        
//					"",					   // range Type
        
//                "",                      // Power Remarks
//
//                "",                      // Sponsor Status
//
//                "",                      // Sponsored By
//
//                "NOT INTERESTED",        // Reason For No Booking
//
//                "NO NEED"                // Remarks
//        );
        
        // ===== FLOW =====
        spectacle.fillSpectacleBookingForm(

                "Yes",                    // Booking Status

                "BIFOCAL GLASSES",       // Spectacle Type
                
                "500-700",               // Range

                "+1.02",            // Power Remarks

                "Yes",                   // Sponsor Status

                "AASTHA",                // Sponsored By

                "",                      // Reason For No Booking

                "Sponsored Booking Done"
        );

        System.out.println("✅ Spectacle Booking Test Completed");
    }


    @AfterMethod
    public void captureFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
        
    }
    
}