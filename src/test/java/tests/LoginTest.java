
package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {

        // Create Login Page Object
        LoginPage login = new LoginPage(driver);

        // Get credentials from config file
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        // Perform Login
        login.login(username, password);
    }
      
}
