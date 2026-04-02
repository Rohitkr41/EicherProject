package pages.camp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.BasePage;

public class CampRegistrationPage extends BasePage {

    public CampRegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By campRegistrationMenu = By.xpath("//span[normalize-space()='Camp Registration']");
    private By newRegistration = By.xpath("(//a[contains(@href,'PatientRegistration_Camp')])[last()]");
    private By campDropdown = By.id("PR_ddlCamp");

    private By firstNameField = By.id("PR_txtFirstName");
    private By lastNameField = By.id("PR_txtLastName");
    private By genderRadioMale = By.name("PR_Gender");
    private By genderRadioFemale = By.id("PR_rdbGenderFemale");

    private By ageYearField = By.id("PR_txtAge");
    private By ageMonthField = By.id("PR_txtAgeMM");       // 🔑 new field
    private By nextOfKinField = By.id("PR_txtNextofKin");     // 🔑 new field
    private By contactNumberField = By.id("PR_txtContactNumber");
    
    private By doorNo = By.id("PR_txtAddress");
    private By district = By.id("PR_txtDistrict");
 // 🔥 Common suggestion list (adjust if needed)
    private By suggestionList = By.xpath("//ul[contains(@class,'ui-autocomplete')]/li");
    
    private By qualificationDropdown = By.id("PR_ddlQualification");
    private By occupationDropdown = By.id("PR_ddlOccupation");
    
    private By drivingLicense = By.id("PR_txtDriverLIcenseNo");
    private By drivingExprience = By.id("PR_txtDriverExp");
    
    private By remarks = By.id("PR_txtOtherRemarks");
    
 // Eye
    private By prevEyeYes = By.id("PrevEyeYes");
    private By prevEyeNo  = By.id("PrevEyeNo");
    private By eyeCenterEMV = By.id("EyeCenterShroff");
    private By eyeCenterOthers = By.id("EyeCenterOthers");
    private By eyeExamDate = By.id("PR_txtEyeWhen");

    // Ear
    private By prevEarYes = By.id("PrevEarYes");
    private By prevEarNo  = By.id("PrevEarNo");
    private By earCenterEMV = By.id("EarCenterShroff");
    private By earCenterOthers = By.id("EarCenterOthers");
    private By earExamDate = By.id("PR_txtEarWhen");

    

    private By submitButton = By.id("PR_btnSubmit");

    // ===== ACTIONS =====

    public void goToNewRegistration() {
        waitForPageReady();

        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(campRegistrationMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

        WebElement newReg = wait.until(ExpectedConditions.presenceOfElementLocated(newRegistration));
        String url = newReg.getAttribute("href");
        driver.get(url);

        wait.until(ExpectedConditions.urlContains("PatientRegistration_Camp"));
        waitForPageReady();
    }

   public void selectCamp(String campName) {

    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(campDropdown));
    Select select = new Select(dropdown);
    select.selectByVisibleText(campName);

    try {
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popup_ok")));
        yesButton.click();

        // ✅ Fast refresh detection (no extra wait)
        wait.until(ExpectedConditions.stalenessOf(dropdown));

        // ✅ Directly wait only for required field (FAST)
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));

    } catch (Exception e) {
        System.out.println("No popup");
    }
}

    public void enterFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        field.clear();
        field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        field.clear();
        field.sendKeys(lastName);
    }

  public void selectGender(String gender) {

    String value = gender.equalsIgnoreCase("Male") ? "M" : "F";
    By genderLocator = By.xpath("//input[@name='PR_Gender' and @value='" + value + "']");

    // 🔁 Retry (kyunki refresh ke baad delay hota hai)
    for (int i = 0; i < 3; i++) {
        try {
            WebElement genderElement = wait.until(ExpectedConditions.presenceOfElementLocated(genderLocator));

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", genderElement);

            // 🔥 JS click (most reliable)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderElement);

            if (genderElement.isSelected()) {
                System.out.println("✅ Gender selected: " + gender);
                return;
            }

        } catch (Exception e) {
            System.out.println("Retrying gender selection...");
        }
    }

    throw new RuntimeException("❌ Gender selection failed after refresh: " + gender);
}


    public void enterAgeYear(String ageYear) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(ageYearField));
        field.clear();
        field.sendKeys(ageYear);
    }

    public void enterAgeMonth(String ageMonth) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(ageMonthField));
        field.clear();
        field.sendKeys(ageMonth);
    }

    public void enterNextOfKin(String nextOfKin) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(nextOfKinField));
        field.clear();
        field.sendKeys(nextOfKin);
    }

    public void enterContactNumber(String contactNumber) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(contactNumberField));
        field.clear();
        field.sendKeys(contactNumber);
    }
    
    public void enterDoorNo(String doorNoValue) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(doorNo));
        field.clear();
        field.sendKeys(doorNoValue);
    }
    
    public void enterdrivingLicense(String dLValue) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(drivingLicense));
        field.clear();
        field.sendKeys(dLValue);
    }
    
    public void enterDrivingExp(String drivingExp) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(drivingExprience));
        field.clear();
        field.sendKeys(drivingExp);
    }
    
    
    public void enterRemarks(String remarksValue) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(remarks));
        field.clear();
        field.sendKeys(remarksValue);
    }
    
 // ===== Eye Section =====
    public void selectPrevEye(boolean isYes) {
        By locator = isYes ? prevEyeYes : prevEyeNo;
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
        try { radio.click(); } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        }
        System.out.println("✅ PrevEye selected: " + (isYes ? "Yes" : "No"));
    }

    public void selectEyeExamCenter(String center) {
        By locator = center.equalsIgnoreCase("EMV") ? eyeCenterEMV : eyeCenterOthers;
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
        try { radio.click(); } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        }
        System.out.println("✅ Eye Exam Center selected: " + center);
    }

    public void enterEyeExamDate(String date) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(eyeExamDate));
        try {
            field.clear();
            field.sendKeys(date);
            System.out.println("✅ Eye Exam Date entered: " + date);
        } catch (InvalidElementStateException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + date + "';", field);
            System.out.println("⚠️ Eye Exam Date set via JS: " + date);
        }
    }

    // ===== Ear Section =====
    public void selectPrevEar(boolean isYes) {
        By locator = isYes ? prevEarYes : prevEarNo;
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
        try { radio.click(); } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        }
        System.out.println("✅ PrevEar selected: " + (isYes ? "Yes" : "No"));
    }

    public void selectEarExamCenter(String center) {
        By locator = center.equalsIgnoreCase("EMV") ? earCenterEMV : earCenterOthers;
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
        try { radio.click(); } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        }
        System.out.println("✅ Ear Exam Center selected: " + center);
    }

    public void enterEarExamDate(String date) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(earExamDate));
        try {
            field.clear();
            field.sendKeys(date);
            System.out.println("✅ Ear Exam Date entered: " + date);
        } catch (InvalidElementStateException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + date + "';", field);
            System.out.println("⚠️ Ear Exam Date set via JS: " + date);
        }
    }

    
    public void selectDistrict(String districtName) {

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(district));
        input.clear();
        input.sendKeys(districtName);

        // 🔁 Retry (important for flaky UI)
        for (int i = 0; i < 3; i++) {
            try {
                // ✅ Wait for suggestions to appear
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionList));

                // ✅ Get all options
                List<WebElement> options = driver.findElements(suggestionList);

                for (WebElement option : options) {
                    String text = option.getText().trim();

                    if (text.equalsIgnoreCase(districtName)) {

                        // 🔥 Scroll + click
                        ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});", option);

                        try {
                            option.click();
                        } catch (Exception e) {
                            // 🔥 JS fallback
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        }

                        System.out.println("✅ District selected: " + text);
                        return;
                    }
                }

            } catch (Exception e) {
                System.out.println("Retrying district selection...");
            }
        }

        throw new RuntimeException("❌ District NOT found: " + districtName);
    }
    
    public void selectDropdownByText(By locator, String value) {

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(dropdown);

        boolean found = false;

        for (WebElement option : select.getOptions()) {
            if (option.getText().trim().equalsIgnoreCase(value)) {
                select.selectByVisibleText(option.getText().trim());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("❌ Option NOT found: " + value);
        }

        System.out.println("✅ Selected: " + value);
    }
    
    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
    
    public void selectQualification(String qualification) {
        new Select(driver.findElement(qualificationDropdown))
            .selectByVisibleText(qualification);
    }

    public void selectOccupation(String occupation) {
        new Select(driver.findElement(occupationDropdown))
            .selectByVisibleText(occupation);
    }

    // ✅ Combined method for full flow
    public void fillCampRegistrationForm(
            String campName,
            String firstName,
            String lastName,
            String gender,
            String ageYear,
            String ageMonth,
            String nextOfKin,
            String contactNumber,
            String doorNo
            
    ) {
        selectCamp(campName);
        enterFirstName(firstName);
        enterLastName(lastName);
        selectGender(gender);
        enterAgeYear(ageYear);
        enterAgeMonth(ageMonth);
        enterNextOfKin(nextOfKin);
        enterContactNumber(contactNumber);
        enterDoorNo(doorNo);
       
        submitForm();
    }

}

