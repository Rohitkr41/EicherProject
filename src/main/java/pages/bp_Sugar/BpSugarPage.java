package pages.bp_Sugar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;

import pages.BasePage;

public class BpSugarPage extends BasePage {

    public BpSugarPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By bpSugarMenu = By.xpath("//span[normalize-space()='BP & Sugar']");
    
 // ===== LOCATORS =====
    private By fromDateField = By.id("BSE_txtFromDate");
    private By toDateField   = By.id("BSE_txtToDate");
    private By searchButton  = By.id("BSE_btnSearch");

    // ✅ Icon locator for "New" status row (row1)
    private By newStatusIcon = By.xpath("//*[@id='row1']/td[45]/a/span/i");
    
    // ✅ Icon locator for "BpSugar"
    private By afterIconExamination = By.xpath("//*[@id=\"bpAndSugar-tab\"]");
    
 // ===== LOCATORS =====
    private By bpDiastolicField = By.id("BPE_Diastolic");   // BP Diastolic
    private By bpSystolicField  = By.id("BPE_Systolic");    // BP Systolic
    private By pulseField       = By.id("BPE_Pulse");       // Pulse
    
 // ===== LOCATORS =====
    private By bpStatusNormalRadio   = By.id("BPE_rdbStatusNormal");
    private By bpStatusAbnormalRadio = By.id("BPE_rdbStatusAbnormal");
    
    private By durationDropdown = By.id("BPE_txtDuration");        // Duration dropdown
    private By periodDropdown   = By.id("BPE_txtPeriod");          // Period dropdown

    private By useMedicineYesRadio = By.id("BPE_rdbMedicineYes");  // Use of Medicine - Yes
    private By useMedicineNoRadio  = By.id("BPE_rdbMedicineNo");   // Use of Medicine - No

    private By remarksField = By.id("BPE_txtRemarks");             // Remarks textfield
    private By saveButton   = By.id("BPE_btnAddUpdateBP");                // Save button


    // ===== ACTION METHODS =====

    
    private By bpCheckedDropdown = By.id("BPE_ddlBPCheckedYesNo");

    // ===== ACTION METHODS =====
    public void openBpSugarPage() {
        click(bpSugarMenu);
    }


// ===== ACTION METHODS =====
	public void searchByDate(String fromDate, String toDate) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Set From Date
    js.executeScript(
        "document.getElementById('BSE_txtFromDate').value='" + fromDate + "';" +
        "$('#BSE_txtFromDate').trigger('change');"
    );

    // Set To Date
    js.executeScript(
        "document.getElementById('BSE_txtToDate').value='" + toDate + "';" +
        "$('#BSE_txtToDate').trigger('change');"
    );

    // Click Search button
    WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    searchBtn.click();

    // Wait for table update
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='row1']")));

    System.out.println("✅ Search executed with FromDate: " + fromDate + " and ToDate: " + toDate);
	}
 

    public void clickNewStatusIcon() {
        click(newStatusIcon);
    }
    
    public void clickNewAfterIconExa() {
        click(afterIconExamination);
    }
    
    // Blood Presure checked dropdown
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
     
  // ===== ACTION METHOD =====
     public void selectBpChecked(String value) {
         WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(bpCheckedDropdown));
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
             throw new RuntimeException("❌ Option NOT found in BP Checked dropdown: " + value);
         }

         System.out.println("✅ BP Checked dropdown selected: " + value);
     }


  // ===== COMBINED FLOW METHOD =====
     public void fillBpSugarExamination(
    	        String diastolic,
    	        String systolic,
    	        String pulse,
    	        String bpStatus,
    	        String duration,
    	        String period,
    	        String useOfMedicine,
    	        String remarks) {

    	    // BP Diastolic
    	    WebElement diastolicInput = wait.until(ExpectedConditions.elementToBeClickable(bpDiastolicField));
    	    diastolicInput.clear();
    	    diastolicInput.sendKeys(diastolic);

    	    // BP Systolic
    	    WebElement systolicInput = wait.until(ExpectedConditions.elementToBeClickable(bpSystolicField));
    	    systolicInput.clear();
    	    systolicInput.sendKeys(systolic);

    	    // Pulse
    	    WebElement pulseInput = wait.until(ExpectedConditions.elementToBeClickable(pulseField));
    	    pulseInput.clear();
    	    pulseInput.sendKeys(pulse);

    	    // BP Status (Radio)
    	    if (bpStatus.equalsIgnoreCase("Normal")) {

    	        WebElement normal = wait.until(ExpectedConditions.presenceOfElementLocated(bpStatusNormalRadio));
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", normal);

    	        // JS click (more reliable)
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", normal);

    	        // Validation
    	        if (!normal.isSelected()) {
    	            throw new RuntimeException("❌ Failed to select Normal BP Status");
    	        }

    	        System.out.println("✅ BP Status selected: Normal");

    	    } else if (bpStatus.equalsIgnoreCase("Abnormal")) {

    	        WebElement abnormal = wait.until(ExpectedConditions.presenceOfElementLocated(bpStatusAbnormalRadio));
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", abnormal);

    	        // JS click
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", abnormal);

    	        // Validation
    	        if (!abnormal.isSelected()) {
    	            throw new RuntimeException("❌ Failed to select Abnormal BP Status");
    	        }

    	        System.out.println("✅ BP Status selected: Abnormal");

    	    } else {
    	        throw new RuntimeException("❌ Invalid BP Status option: " + bpStatus);
    	    }


    	    // Duration dropdown
    	    WebElement durationDrop = wait.until(ExpectedConditions.elementToBeClickable(durationDropdown));
    	    new Select(durationDrop).selectByVisibleText(duration);

    	    // Period dropdown
    	    WebElement periodDrop = wait.until(ExpectedConditions.elementToBeClickable(periodDropdown));
    	    new Select(periodDrop).selectByVisibleText(period);

    	    // Use of Medicine (Radio)
    	    if (useOfMedicine.equalsIgnoreCase("Yes")) {

    	        WebElement yes = wait.until(ExpectedConditions.presenceOfElementLocated(useMedicineYesRadio));
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yes);

    	        // JS Click (force click)
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yes);

    	        // Validation
    	        if (!yes.isSelected()) {
    	            throw new RuntimeException("❌ Failed to select Use of Medicine: Yes");
    	        }

    	        System.out.println("✅ Use of Medicine selected: Yes");

    	    } else if (useOfMedicine.equalsIgnoreCase("No")) {

    	        WebElement no = wait.until(ExpectedConditions.presenceOfElementLocated(useMedicineNoRadio));
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", no);

    	        // JS Click
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", no);

    	        // Validation
    	        if (!no.isSelected()) {
    	            throw new RuntimeException("❌ Failed to select Use of Medicine: No");
    	        }

    	        System.out.println("✅ Use of Medicine selected: No");

    	    } else {
    	        throw new RuntimeException("❌ Invalid Use of Medicine option: " + useOfMedicine);
    	    }

    	    // Remarks
    	    WebElement remarksInput = wait.until(ExpectedConditions.elementToBeClickable(remarksField));
    	    remarksInput.clear();
    	    remarksInput.sendKeys(remarks);

    	    // Save button
    	    WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
    	    saveBtn.click();
    	    handleSuccessPopup();

    	    System.out.println("✅ BP & Sugar Examination form filled and saved successfully!");
    	}
     
     
     
     	//Sugar checked Dropdown
     
     private By sugarCheckedDropdown = By.id("Sugar_ddlChecked");
     private By sugarRandom = By.id("SE_Random");
     
     private By sugarAbnormalLabel = By.xpath("//label[@for='SE_rdbAbnormal']");
     private By sugarNormalLabel   = By.xpath("//label[@for='SE_rdbNormal']");
     
     private By sugarAbnormalRadio = By.id("SE_rdbAbnormal");
     private By sugarNormalRadio   = By.id("SE_rdbNormal");
     
     private By sugarDurationDropdown = By.id("SE_txtDuration");        // Duration dropdown
     private By sugarPeriodDropdown   = By.id("SE_txtPeriod");          // Period dropdown

     private By sugarUseMedicineYesRadio = By.id("SE_rdbMedicineYes");  // Use of Medicine - Yes
     private By sugarUseMedicineNoRadio  = By.id("SE_rdbMedicineNo");   // Use of Medicine - No

     private By sugarRemarksField = By.id("SE_txtRemarks");             // Remarks textfield
     private By sugarSaveButton   = By.id("SE_btnSave");                // Save button
     
     // ===== ACTION METHOD =====
     public void selectSugarChecked(String value) {
         WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sugarCheckedDropdown));
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
             throw new RuntimeException("❌ Option NOT found in BP Checked dropdown: " + value);
         }

         System.out.println("✅ BP Checked dropdown selected: " + value);
     }


  // ===== COMBINED FLOW METHOD =====
     
     public void fillSugarExamination(
        String randomSugar,
        String sugarStatus,
        String duration,
        String period,
        String useOfMedicine,
        String remarks) {

    // Random Sugar
    WebElement randomInput = wait.until(ExpectedConditions.elementToBeClickable(sugarRandom));
    randomInput.clear();
    randomInput.sendKeys(randomSugar);

    // Sugar Status
  
    if (sugarStatus.equalsIgnoreCase("Normal")) {

        WebElement normal = wait.until(ExpectedConditions.elementToBeClickable(sugarNormalLabel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", normal);
        normal.click();

        // validation
        if (!wait.until(ExpectedConditions.elementToBeSelected(sugarNormalRadio))) {
            throw new RuntimeException("❌ Normal NOT selected");
        }

        System.out.println("✅ Sugar Status selected: Normal");

    } else if (sugarStatus.equalsIgnoreCase("Abnormal")) {

        WebElement abnormal = wait.until(ExpectedConditions.elementToBeClickable(sugarAbnormalLabel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", abnormal);
        abnormal.click();

        // validation
        if (!wait.until(ExpectedConditions.elementToBeSelected(sugarAbnormalRadio))) {
            throw new RuntimeException("❌ Abnormal NOT selected");
        }

        System.out.println("✅ Sugar Status selected: Abnormal");

    } else {
        throw new RuntimeException("❌ Invalid Sugar Status");
    }
    
    
    // Dropdowns (reusable method)
    selectDropdownByText(sugarDurationDropdown, duration);
    selectDropdownByText(sugarPeriodDropdown, period);

    // Use of Medicine
    if (useOfMedicine.equalsIgnoreCase("Yes")) {

        WebElement yes = wait.until(ExpectedConditions.presenceOfElementLocated(sugarUseMedicineYesRadio));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yes);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yes);

        if (!yes.isSelected()) throw new RuntimeException("❌ Yes not selected");

    } else if (useOfMedicine.equalsIgnoreCase("No")) {

        WebElement no = wait.until(ExpectedConditions.presenceOfElementLocated(sugarUseMedicineNoRadio));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", no);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", no);

        if (!no.isSelected()) throw new RuntimeException("❌ No not selected");

    } else {
        throw new RuntimeException("❌ Invalid Use of Medicine");
    }

    // Remarks
    WebElement remarksInput = wait.until(ExpectedConditions.elementToBeClickable(sugarRemarksField));
    remarksInput.clear();
    remarksInput.sendKeys(remarks);

    // Save
    WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(sugarSaveButton));
    saveBtn.click();

    handleSuccessPopup();

    System.out.println("✅ Sugar flow completed");
}

     
     //Referal field code
     
     private By referralYesLabel = By.xpath("//label[@for='BSEReferral_rdbYes']");
     private By referralNoLabel  = By.xpath("//label[@for='BSEReferral_rdbNo']");

     private By referralYesRadio = By.id("BSEReferral_rdbYes");
     private By referralNoRadio  = By.id("BSEReferral_rdbNo");
     
     public void selectReferralRequired(String value) {

    if (value.equalsIgnoreCase("Yes")) {

        WebElement yes = wait.until(ExpectedConditions.elementToBeClickable(referralYesLabel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yes);
        yes.click();

        // ✅ validation (important)
        if (!wait.until(ExpectedConditions.elementToBeSelected(referralYesRadio))) {
            throw new RuntimeException("❌ Referral YES not selected");
        }

        System.out.println("✅ Referral Required: Yes");

    } else if (value.equalsIgnoreCase("No")) {

        WebElement no = wait.until(ExpectedConditions.elementToBeClickable(referralNoLabel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", no);
        no.click();

        if (!wait.until(ExpectedConditions.elementToBeSelected(referralNoRadio))) {
            throw new RuntimeException("❌ Referral NO not selected");
        }

        System.out.println("✅ Referral Required: No");

    } else {
        throw new RuntimeException("❌ Invalid Referral value: " + value);
    }
}
     
     private By referalRemarksField = By.id("BSEReferral_txtRemarks");
     
     public void enterRemarks(String remarks) {

    	    WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(referalRemarksField));
    	    field.clear();
    	    field.sendKeys(remarks);

    	    System.out.println("✅ Remarks entered: " + remarks);
    	}
  
     
     private By saveBtn = By.id("BSE_btnReffralSave");
     
     public void clickSave() {

    	    WebElement save = wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", save);
    	    save.click();

    	    System.out.println("✅ Clicked on Save button");
    	}
     
     private By referCheckOutBtn = By.id("BSE_btnCheckout");
     
     public void clickReferForClinicalExamination() {

    	    WebElement refer = wait.until(ExpectedConditions.elementToBeClickable(referCheckOutBtn));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", refer);
    	    refer.click();

    	    System.out.println("✅ Clicked on Refer for Clinical Examination");
    	}
     
     private By successPopup = By.id("popup_message");
     private By okButton     = By.id("popup_ok");
     
     public void handleSuccessPopup() {

    	    // Wait for popup visible
    	    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));

    	    // Print message
    	    System.out.println("✅ Popup Message: " + popup.getText());

    	    // Click OK button
    	    WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
    	    okBtn.click();

    	    // Wait for popup to disappear
    	    wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));

    	    System.out.println("✅ Popup handled successfully");
    	}

}
