package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By loader = By.cssSelector(".loader, .spinner, .loading");
    private final By modal = By.cssSelector(".custom-modal");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void waitForPageReady() {
        waitForLoaderToDisappear();
        waitUntilModalGone();
    }

    protected void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (Exception ignored) {}
    }

    protected void waitUntilModalGone() {
        try {
            List<WebElement> modals = driver.findElements(modal);
            for (WebElement m : modals) {
                if (m.isDisplayed()) {
                    List<WebElement> ok = m.findElements(By.xpath(".//button[text()='OK']"));
                    if (!ok.isEmpty()) {
                        ok.get(0).click();
                    }
                    wait.until(ExpectedConditions.invisibilityOf(m));
                }
            }
        } catch (Exception ignored) {}
    }

    protected WebElement waitForVisibility(By locator) {
        waitForPageReady();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        waitForPageReady();
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        WebElement el = waitForClickable(locator);
        scrollToElement(el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement el = waitForClickable(locator);
        new Select(el).selectByVisibleText(text);
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitForUrlContains(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }

    protected void scrollToElement(WebElement el) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
}