	package pages.clinicalExamination;

	import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

	public class OcularHistoryPage extends BasePage {

    public OcularHistoryPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By ocularHistoryTab = By.id("ocularSystemicDisease-tab");

    private By diseaseDropdown = By.id("s2id_CE_ddlOcularDiseaseName");
    private By eyeDropdown     = By.id("s2id_CE_txtOcularDiseaseEyeSubSection");

    private By surgeryDropdown = By.id("s2id_CE_ddlOcularDiseaseSurgery");
 // ===== STATUS DROPDOWN =====
    private By statusDropdown = By.id("CE_ddlOcularDiseaseStatus");

    private By remarksField    = By.id("CE_ddlOcularDiseaseRemarks");

    private By saveButton      = By.id("CE_btnAddUpdateOcularDisease");

    private By successPopup = By.id("popup_message");
    private By okButton     = By.id("popup_ok");

    // ===== TAB =====
    public void openOcularHistoryTab() {
        WebElement tab = wait.until(
                ExpectedConditions.presenceOfElementLocated(ocularHistoryTab));

        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }
    }

	    // ===== SELECT2 HANDLER (FINAL FIX) =====
	  private void selectSelect2Dropdown(By containerLocator, String value) {
	
	    // 1️⃣ Click container
	    WebElement container = wait.until(ExpectedConditions.elementToBeClickable(containerLocator));
	    container.click();
	
	    // 2️⃣ Wait for dropdown
	    WebElement dropdown = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));
	
	    // 3️⃣ Type in search box (if exists)
	    try {
	        WebElement searchBox = dropdown.findElement(By.cssSelector("input.select2-input"));
	        searchBox.clear();
	        searchBox.sendKeys(value);
	    } catch (NoSuchElementException ignored) {}
	
	    // 4️⃣ Wait for EXACT visible option
	    By optionLocator = By.xpath(
	            "//div[@id='select2-drop']//li[contains(@class,'select2-result-selectable') and normalize-space()='" + value + "']"
	    );
	
	    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
	
	    // 5️⃣ Click option
	    option.click();
	
	    System.out.println("🟢 Selected: " + value);
	}
	
	    // ===== FIELD METHODS =====
	
	    public void selectDisease(String disease) {
	        selectSelect2Dropdown(diseaseDropdown, disease);
	    }
	
	    public void selectEye(String eye) {
	    By eyeDropdownLocator = By.id("CE_txtOcularDiseaseEyeSubSection");
	    WebElement eyeDropdown = wait.until(ExpectedConditions.elementToBeClickable(eyeDropdownLocator));
	    Select select = new Select(eyeDropdown);
	    
	    // Since option text is "BOTH", "RE", "LE" etc, use visible text to select
	    select.selectByVisibleText(eye);
	    
	    System.out.println("🟢 Selected eye: " + eye);
	}
	
	    public void selectSurgeryTreatmentDone(String value) {
	    // Updated to match the actual Select2 container for Surgery/Treatment
	    By surgeryDropdownContainer = By.id("s2id_CE_ddlOcularDiseaseSurgery");
	
	    selectSelect2Dropdown(surgeryDropdownContainer, value);
	}

    public void selectStatus(String status) {
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(statusDropdown));

        Select select = new Select(dropdown);
        select.selectByVisibleText(status);

        System.out.println("🟢 Selected Status: " + status);
    }

    public void enterRemarks(String remarks) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(remarksField));

        input.clear();
        input.sendKeys(remarks);

        System.out.println("🟢 Entered Remarks: " + remarks);
    }
    
    public void clickSaveButton() {
        WebElement saveBtn = wait.until(
                ExpectedConditions.elementToBeClickable(saveButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", saveBtn);

        saveBtn.click();

        System.out.println("🟢 Clicked Save button");
    }

    // ===== NORMAL DROPDOWN =====
    private void selectDropdown(By locator, String value) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator));

        if (element.getTagName().equalsIgnoreCase("select")) {
            new Select(element).selectByVisibleText(value);
        } else {
            element.click();

            By option = By.xpath("//li[normalize-space()='" + value + "']");
            WebElement optionElement = wait.until(
                    ExpectedConditions.elementToBeClickable(option));
            optionElement.click();
        }
    }


    // ===== POPUP =====
    public void handleSuccessPopup() {

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopup));

        System.out.println("✅ Popup Message: " + popup.getText());

        WebElement okBtn = wait.until(
                ExpectedConditions.elementToBeClickable(okButton));
        okBtn.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    // ===== UTIL =====
    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

    // ===== COMPLETE FLOW =====
    public void fillOcularHistory(String disease, String eye,
                                 String surgery, String status, String remarks) {

    	openOcularHistoryTab();
    	selectDisease(disease);
    	selectEye(eye);
    	selectSurgeryTreatmentDone(surgery); // <-- now correct
    	selectStatus(status);
    	enterRemarks(remarks);
    	clickSaveButton();
    }
    
    // ===== SYSTEMIC DISEASE SECTION =====

	 // Any of the following
	 private By anyOfDropdown = By.id("CE_ddlSystemicDiseasesYesNo");
	
	 // Systemic Disease (Select2)
	 private By systemicDiseaseDropdown = By.id("s2id_CE_ddlAUSystemicHistory");
	
	 // Duration
	 private By durationDropdown = By.id("CE_ddlSYSPeriod");
	
	 // Period
	 private By periodDropdown = By.id("CE_ddlSYSDuration");
	
	 // Status
	 private By systemicStatusDropdown = By.id("CE_ddlSYSStatus");
	
	 // Remarks
	 private By systemicRemarksField = By.id("CE_ddlSystemiceRemarks");
	
	 // Save button
	 private By systemicSaveButton = By.id("CE_btnAddUpdateSystemicDiseases");
	 
	 
		public void selectAnyOf(String value) {
	
	    WebElement dropdown = wait.until(
	            ExpectedConditions.presenceOfElementLocated(anyOfDropdown));
	
	    // 🔥 Small pause before interaction (UI settle hone ke liye)
	    try { Thread.sleep(500); } catch (InterruptedException e) {}
	
	    String val = value.equalsIgnoreCase("yes") ? "1"
	               : value.equalsIgnoreCase("no")  ? "0"
	               : "-1";
	
	    // 🔥 STEP 1: JS select (stable)
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].value='" + val + "';" +
	            "arguments[0].dispatchEvent(new Event('change'));", dropdown);
	
	    // 🔥 STEP 2: WAIT until value sticks (retry internally)
	    wait.until(driver -> {
	        try {
	            String current = new Select(driver.findElement(anyOfDropdown))
	                    .getFirstSelectedOption().getAttribute("value");
	            return current.equals(val);
	        } catch (StaleElementReferenceException e) {
	            return false;
	        }
	    });
	
	    // 🔥 STEP 3: EXTRA STABILITY WAIT (IMPORTANT)
	    try { Thread.sleep(800); } catch (InterruptedException e) {}
	
	    // 🔥 STEP 4: FINAL VERIFY + retry once
	    Select select = new Select(wait.until(
	            ExpectedConditions.elementToBeClickable(anyOfDropdown)));
	
	    String finalValue = select.getFirstSelectedOption().getAttribute("value");
	
	    if (!finalValue.equals(val)) {
	        System.out.println("⚠️ Re-selecting due to reset issue");
	
	        select.selectByValue(val);
	
	        wait.until(d -> new Select(d.findElement(anyOfDropdown))
	                .getFirstSelectedOption().getAttribute("value").equals(val));
	    }
	
	    System.out.println("🟢 Final Selected Any Of: " + value);
	
	    // 🔥 STEP 5: WAIT for dependent dropdown (VERY IMPORTANT)
	    wait.until(ExpectedConditions.visibilityOfElementLocated(systemicDiseaseDropdown));
	}

 
	 public void selectSystemicDisease(String disease) {
		    selectSelect2Dropdown(systemicDiseaseDropdown, disease);
		}
	 
	 public void selectDuration(String duration) {
		    new Select(wait.until(
		            ExpectedConditions.elementToBeClickable(durationDropdown)))
		            .selectByVisibleText(duration);
	
		    System.out.println("🟢 Selected Duration: " + duration);
		}
	 
	 public void selectPeriod(String period) {
		    new Select(wait.until(
		            ExpectedConditions.elementToBeClickable(periodDropdown)))
		            .selectByVisibleText(period);
	
		    System.out.println("🟢 Selected Period: " + period);
		}
	 
	 public void selectSystemicStatus(String status) {
		    new Select(wait.until(
		            ExpectedConditions.elementToBeClickable(systemicStatusDropdown)))
		            .selectByVisibleText(status);
	
		    System.out.println("🟢 Selected Systemic Status: " + status);
		}
	 
	 public void enterSystemicRemarks(String remarks) {
		    WebElement input = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(systemicRemarksField));
	
		    input.clear();
		    input.sendKeys(remarks);
	
		    System.out.println("🟢 Entered Systemic Remarks");
		}
 
	 public void clickSystemicSaveButton() {
		    WebElement saveBtn = wait.until(
		            ExpectedConditions.elementToBeClickable(systemicSaveButton));
	
		    ((JavascriptExecutor) driver).executeScript(
		            "arguments[0].scrollIntoView({block:'center'});", saveBtn);
	
		    saveBtn.click();
	
		    System.out.println("🟢 Clicked Systemic Save button");
		}
 
		public void fillSystemicDisease(String anyOf, String disease,
	        String duration, String period,
	        String status, String remarks) {
	
	    selectAnyOf(anyOf);                 // MUST be Yes
	    selectSystemicDisease(disease);     // Select2
	    selectDuration(duration);           // Fixed locator
	    selectPeriod(period);               // Fixed locator
	    selectSystemicStatus(status);
	    enterSystemicRemarks(remarks);
	    clickSystemicSaveButton();
	
	    handleSuccessPopup();
	}
 
 
}
