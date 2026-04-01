package pages.camp;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

public class CampRegistrationPage extends BasePage {

    public CampRegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By selectCampDropdown =
            By.xpath("//label[contains(text(),'Select Camp')]/following::select[1]");

    private By newRegistration =
            By.xpath("(//span[normalize-space()='Camp Registration'])[last()]");

    // ===== ACTION =====
    public void selectCamp(String campName) {
        selectByVisibleText(selectCampDropdown, campName);
        waitForPageReady();
    }

  public void clickNewRegistration() {

    waitForPageReady();

    // ✅ Card wala exact element pakdo (NOT sidebar)
    By newRegistration = By.xpath("(//span[normalize-space()='Camp Registration'])[last()]");

    WebElement element = wait.until(
            ExpectedConditions.presenceOfElementLocated(newRegistration));

    // Scroll center
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", element);

    // छोटा wait for rendering
    try { Thread.sleep(800); } catch (Exception e) {}

    // ✅ REAL FIX → Angular/React click event fire
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true}))", element
    );

    // ✅ Wait navigation
    waitForUrlContains("registration");
}
  
  
}