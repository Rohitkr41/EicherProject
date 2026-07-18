package pages.camp;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.CampRegistrationData;
import utils.TestDataGenerator;
import com.github.javafaker.Faker;
import java.util.Locale;

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
	
	private By ageYearField = By.id("PR_txtAge");
	private By ageMonthField = By.id("PR_txtAgeMM"); // 🔑 new field
	private By nextOfKinField = By.id("PR_txtNextofKin"); // 🔑 new field
	private By contactNumberField = By.id("PR_txtContactNumber");

	private By doorNo = By.id("PR_txtAddress");
	private By district = By.id("PR_txtDistrict");
	
	private By qualificationDropdown = By.id("PR_ddlQualification");
	private By occupationDropdown = By.id("PR_ddlOccupation");
	private By identityTypeDropdown = By.id("PR_ddlIdentityType");

	private By drivingLicense = By.id("PR_txtDriverLIcenseNo");
	private By drivingExprience = By.id("PR_txtDriverExp");

	private By remarks = By.id("PR_txtOtherRemarks");

	// Eye
	private By prevEyeYes = By.id("PrevEyeYes");
	private By prevEyeNo = By.id("PrevEyeNo");
	private By eyeCenterEMV = By.id("EyeCenterShroff");
	private By eyeCenterOthers = By.id("EyeCenterOthers");
	private By eyeExamDate = By.id("PR_txtEyeWhen");

	// Ear
	private By prevEarYes = By.id("PrevEarYes");
	private By prevEarNo = By.id("PrevEarNo");
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
	
	public void selectFirstCamp() {

	    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(campDropdown));

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", dropdown);

	    dropdown.click();

	    Select select = new Select(dropdown);

	    for (WebElement option : select.getOptions()) {

	        String text = option.getText().trim();
	        String value = option.getAttribute("value");

	        if (!text.isEmpty()
	                && value != null
	                && !value.trim().isEmpty()
	                && !text.equalsIgnoreCase("Select")
	                && !text.equalsIgnoreCase("--Select--")
	                && !text.equalsIgnoreCase("Select Camp")) {

	            select.selectByVisibleText(text);
	            System.out.println("Camp selected: " + text);
	            break;
	        }
	    }

	    try {
	        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popup_ok")));
	        yesButton.click();

	        wait.until(ExpectedConditions.stalenessOf(dropdown));
	        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));

	    } catch (Exception e) {
	        System.out.println("No popup");
	    }
	}
	
	public void selectFirstCampIfNotSelected() {

    waitForPageReady();

    try {
        wait.until(ExpectedConditions.urlContains("PatientRegistration_Camp"));
    } catch (Exception e) {
        System.out.println("Registration page URL confirm nahi hua, continuing...");
    }

    WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(campDropdown));

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", dropdown);

    if (!dropdown.isDisplayed()) {
        throw new RuntimeException("Camp dropdown visible nahi hai");
    }

    Select select = new Select(dropdown);

    String selectedText = select.getFirstSelectedOption().getText().trim();
    String selectedValue = select.getFirstSelectedOption().getAttribute("value");

    boolean campAlreadySelected =
            selectedValue != null
            && !selectedValue.trim().isEmpty()
            && !selectedText.equalsIgnoreCase("Select")
            && !selectedText.equalsIgnoreCase("--Select--")
            && !selectedText.equalsIgnoreCase("Select Camp");

    if (campAlreadySelected) {
        System.out.println("Camp already selected: " + selectedText);
        return;
    }

    wait.until(ExpectedConditions.elementToBeClickable(campDropdown));
    dropdown.click();

    boolean selected = false;

    for (WebElement option : select.getOptions()) {

        String text = option.getText().trim();
        String value = option.getAttribute("value");

        if (!text.isEmpty()
                && value != null
                && !value.trim().isEmpty()
                && !text.equalsIgnoreCase("Select")
                && !text.equalsIgnoreCase("--Select--")
                && !text.equalsIgnoreCase("Select Camp")) {

            select.selectByVisibleText(text);
            System.out.println("Camp selected: " + text);
            selected = true;
            break;
        }
    }

    if (!selected) {
        throw new RuntimeException("Camp dropdown mein valid camp option nahi mila");
    }

    try {
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popup_ok")));
        yesButton.click();

        wait.until(ExpectedConditions.stalenessOf(dropdown));
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));

    } catch (Exception e) {
        System.out.println("No popup after camp selection");
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
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

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
						genderElement);

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
    try {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(drivingLicense));

        if (!field.isDisplayed() || !field.isEnabled()) {
            System.out.println("Driving License field disabled/hidden hai, skip");
            return;
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", field);

        field.clear();
        field.sendKeys(dLValue);

        System.out.println("Driving License entered");
    } catch (Exception e) {
        System.out.println("Driving License field interactable nahi hai, skip");
    }
}

	public void enterDrivingExp(String drivingExp) {
    try {
        if (drivingExp == null || drivingExp.trim().isEmpty()) {
            return;
        }

        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(drivingExprience));

        if (!field.isDisplayed() || !field.isEnabled()) {
            System.out.println("Driving Experience field disabled/hidden hai, skip");
            return;
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", field);

        field.clear();
        field.sendKeys(drivingExp.trim());

        System.out.println("Driving Experience entered: " + drivingExp);
    } catch (Exception e) {
        System.out.println("Driving Experience field interactable nahi hai, skip");
    }
}

	private boolean isDrivingLicenseRequired(CampRegistrationData patient) {

	    String occupation = patient.getOccupation();
	    String identityType = patient.getIdentityType();

	    boolean occupationIsDriver = occupation != null
	            && occupation.trim().equalsIgnoreCase("DRIVER");

	    boolean identityIsDrivingLicense = identityType != null
	            && identityType.trim().equalsIgnoreCase("DRIVING LICENSE");

	    return occupationIsDriver || identityIsDrivingLicense;
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
		try {
			radio.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
		}
		System.out.println("✅ PrevEye selected: " + (isYes ? "Yes" : "No"));
	}

	public void selectEyeExamCenter(String center) {
		By locator = center.equalsIgnoreCase("EMV") ? eyeCenterEMV : eyeCenterOthers;
		WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
		try {
			radio.click();
		} catch (Exception e) {
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
		try {
			radio.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
		}
		System.out.println("✅ PrevEar selected: " + (isYes ? "Yes" : "No"));
	}

	public void selectEarExamCenter(String center) {
		By locator = center.equalsIgnoreCase("EMV") ? earCenterEMV : earCenterOthers;
		WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio);
		try {
			radio.click();
		} catch (Exception e) {
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

		if (districtName == null || districtName.trim().isEmpty()) {
			System.out.println("District blank hai, skip kar raha hoon");
			return;
		}

		districtName = districtName.trim();

		WebElement input = wait.until(ExpectedConditions.elementToBeClickable(district));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);

		input.click();
		input.clear();
		input.sendKeys(districtName);

		By suggestionsLocator = By
				.xpath("//ul[contains(@class,'ui-autocomplete') and not(contains(@style,'display: none'))]//li");

		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(suggestionsLocator, 0));

		List<WebElement> suggestions = driver.findElements(suggestionsLocator);

		System.out.println("Total District Suggestions: " + suggestions.size());

		for (WebElement suggestion : suggestions) {

			String text = suggestion.getText().trim();
			System.out.println("District suggestion: " + text);

			if (text.equalsIgnoreCase(districtName) || text.toLowerCase().contains(districtName.toLowerCase())) {

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
						suggestion);

				try {
					new Actions(driver).moveToElement(suggestion).click().perform();
				} catch (Exception e1) {
					try {
						WebElement clickableChild = suggestion.findElement(By.xpath(".//*"));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableChild);
					} catch (Exception e2) {
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", suggestion);
					}
				}

				wait.until(ExpectedConditions.attributeToBeNotEmpty(input, "value"));

				System.out.println("District selected: " + text);
				return;
			}
		}

		// Final fallback: first suggestion select by keyboard
		input.click();
		input.sendKeys(Keys.ARROW_DOWN);
		input.sendKeys(Keys.ENTER);

		System.out.println("District selected using keyboard fallback: " + districtName);
	}

	public void submitForm() {
		wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
	}

	public void selectQualification(String qualification) {
		if (qualification == null || qualification.trim().isEmpty()) {
			return;
		}

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(qualificationDropdown));
		new Select(dropdown).selectByVisibleText(qualification.trim());
	}

	public void selectOccupation(String occupation) {
		if (occupation == null || occupation.trim().isEmpty()) {
			return;
		}

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(occupationDropdown));
		new Select(dropdown).selectByVisibleText(occupation.trim());
	}
	
	public void selectIdentityType(String identityType) {

	    if (identityType == null || identityType.trim().isEmpty()) {
	        System.out.println("Identity Type blank hai, skip");
	        return;
	    }

	    WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(identityTypeDropdown));

	    if (!dropdown.isDisplayed() || !dropdown.isEnabled()) {
	        System.out.println("Identity Type disabled hai, skip. Value may be auto selected.");
	        return;
	    }

	    new Select(dropdown).selectByVisibleText(identityType.trim());
	    System.out.println("Identity Type selected: " + identityType);
	}

	private void fillEyeDetails(CampRegistrationData patient) {

		String previousEye = patient.getPreviousEyeCheckup();

		if (previousEye == null || previousEye.trim().isEmpty())
			return;

		boolean isYes = previousEye.trim().equalsIgnoreCase("YES");
		selectPrevEye(isYes);

		if (isYes) {
			if (patient.getEyeExaminedCenter() != null && !patient.getEyeExaminedCenter().trim().isEmpty()) {
				selectEyeExamCenter(patient.getEyeExaminedCenter().trim());
			}

			String eyeDate = patient.getEyeExaminedDate();

			if (eyeDate == null || eyeDate.trim().isEmpty()) {
				eyeDate = TestDataGenerator.getPastDate();
			}

			enterEyeExamDate(eyeDate);
		}
	}

	private void fillEarDetails(CampRegistrationData patient) {

		String previousEar = patient.getPreviousEarCheckup();

		if (previousEar == null || previousEar.trim().isEmpty())
			return;

		boolean isYes = previousEar.trim().equalsIgnoreCase("YES");
		selectPrevEar(isYes);

		if (isYes) {
			if (patient.getEarExaminedCenter() != null && !patient.getEarExaminedCenter().trim().isEmpty()) {
				selectEarExamCenter(patient.getEarExaminedCenter().trim());
			}

			String earDate = patient.getEarExaminedDate();

			if (earDate == null || earDate.trim().isEmpty()) {
				earDate = TestDataGenerator.getPastDate();
			}

			enterEarExamDate(earDate);
		}
	}

	// ✅ Combined method for full flow
//	public void fillCampRegistrationForm(CampRegistrationData patient) {
//
//		enterFirstName(TestDataGenerator.getFirstName());
//		enterLastName(TestDataGenerator.getLastName());
//
//		selectGender(patient.getGender());
//
//		enterAgeYear(patient.getAgeYear());
//		enterAgeMonth(patient.getAgeMonth());
//
//		enterNextOfKin(patient.getNextOfKin());
//		enterContactNumber(TestDataGenerator.getMobileNumber());
//		enterDoorNo(TestDataGenerator.getAddress());
//
//		selectDistrict(patient.getDistrict());
//
//		selectQualification(patient.getQualification());
//		selectOccupation(patient.getOccupation());
//
//		if (!"DRIVER".equalsIgnoreCase(patient.getOccupation())) {
//		    selectIdentityType(patient.getIdentityType());
//		} else {
//		    System.out.println("Occupation DRIVER hai, Identity Type auto selected/disabled hoga");
//		}
//
//		if (isDrivingLicenseRequired(patient)) {
//		    enterdrivingLicense(TestDataGenerator.getDrivingLicense());
//		    enterDrivingExp(patient.getDrivingExp());
//		} else {
//		    System.out.println("Identity Type Driving License nahi hai, DL/Experience skip");
//		}
//
//		enterdrivingLicense(TestDataGenerator.getDrivingLicense());
//		enterDrivingExp(patient.getDrivingExp());
//
//		fillEyeDetails(patient);
//		fillEarDetails(patient);
//
//		enterRemarks(patient.getRemarks());
//	}
	
	public void fillCampRegistrationForm(CampRegistrationData patient) {
		 selectFirstCampIfNotSelected();

	    Faker faker = new Faker(new Locale("en", "IN"));

	    String firstName = faker.name().firstName();
	    String lastName = faker.name().lastName();

	    System.out.println("Generated First Name : " + firstName);
	    System.out.println("Generated Last Name  : " + lastName);

	    enterFirstName(firstName);
	    enterLastName(lastName);

	    selectGender(patient.getGender());

	    enterAgeYear(patient.getAgeYear());
	    enterAgeMonth(patient.getAgeMonth());

	    enterNextOfKin(patient.getNextOfKin());
	    enterContactNumber(TestDataGenerator.getMobileNumber());
	    enterDoorNo(TestDataGenerator.getAddress());
	    selectDistrict(patient.getDistrict());

		selectQualification(patient.getQualification());
		selectOccupation(patient.getOccupation());

		if (!"DRIVER".equalsIgnoreCase(patient.getOccupation())) {
			selectIdentityType(patient.getIdentityType());
		} else {
			System.out.println("Occupation DRIVER hai, Identity Type auto selected/disabled hoga");
		}

		if (isDrivingLicenseRequired(patient)) {
			enterdrivingLicense(TestDataGenerator.getDrivingLicense());
			enterDrivingExp(patient.getDrivingExp());
		} else {
			System.out.println("Identity Type Driving License nahi hai, DL/Experience skip");
		}

		enterdrivingLicense(TestDataGenerator.getDrivingLicense());
		enterDrivingExp(patient.getDrivingExp());

		fillEyeDetails(patient);
		fillEarDetails(patient);

		enterRemarks(patient.getRemarks());
	}

}
