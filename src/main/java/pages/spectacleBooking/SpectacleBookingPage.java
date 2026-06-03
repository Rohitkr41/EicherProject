package pages.spectacleBooking;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.BasePage;

public class SpectacleBookingPage extends BasePage {

    public SpectacleBookingPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====

    // Booking Status
    private By searchButton  = By.id("SB_btnSearch");
    private By bookingYesRadio = By.id("SB_rdbBookingYes");
    
    private By bookingNoRadio  = By.id("SB_rdbBookingNo");
   
    
    // Spectacle Type Dropdown
    private By spectacleTypeDropdown = By.id("SB_ddlSpectacleTypeId");
    
 // ===== RANGE DROPDOWN =====

    private By rangeDropdown = By.id("SB_Range");

    // Power Remarks
    private By powerRemarksField = By.id("SB_PowerRemarks");

    // Sponsored Radio
    private By sponsorYesRadio = By.id("SB_rdbSponsorYes");
    private By sponsorNoRadio  = By.id("SB_rdbSponsorNo");

    // Sponsored By Dropdown
    private By sponsoredByDropdown = By.id("SB_SponsoredBy");

    // Reason For No Booking
    private By reasonForNoBookingDropdown = By.id("SB_ReasonNoBooking");

    // Remarks
    private By remarksField = By.id("SB_Remark");

    // Save Button
    private By saveButton = By.id("SB_btnSubmit");

    // Popup
    private By successPopup = By.id("popup_message");
    private By okButton     = By.id("popup_ok");
    
    
	public boolean clickSpectacleBookingIcon() {

    JavascriptExecutor js = (JavascriptExecutor) driver;

    // ===== Wait For Row =====
    WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("row1")));

    // ===== Get Status =====
    WebElement statusElement = row.findElement(
            By.xpath(".//span[contains(@class,'label')]"));

    String statusText = statusElement.getText().trim();

    System.out.println("Current Status : " + statusText);

    // ===== If Already Booked =====
    if (statusText.equalsIgnoreCase("Booked")) {

        System.out.println("⚠ Record Already Booked - Test Passed");

        return false;
    }

    // ===== Click Booking Icon =====
    WebElement icon = row.findElement(
            By.xpath(".//td[@name='fieldThirtyFive']"));

    wait.until(ExpectedConditions.visibilityOf(icon));

    js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            icon);

    js.executeScript("arguments[0].click();", icon);

    System.out.println("✅ Spectacle Booking Icon Clicked");

    return true;
}

    // ===== COMMON DROPDOWN METHOD =====

    public void selectDropdownByText(By locator, String value) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(locator));

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

        System.out.println("✅ Selected Dropdown Value: " + value);
    }
    
    public void searchByDate(String fromDate, String toDate) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set From Date
        js.executeScript(
            "document.getElementById('SB_txtFromDate').value='" + fromDate + "';" +
            "$('#BSE_txtFromDate').trigger('change');"
        );

        // Set To Date
        js.executeScript(
            "document.getElementById('SB_txtToDate').value='" + toDate + "';" +
            "$('#BSE_txtToDate').trigger('change');" 
        );

        // Click Search button
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();

        // Wait for table update
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='row1']")));

        System.out.println("✅ Search executed with FromDate: " + fromDate + " and ToDate: " + toDate);
    	}


    // ===== BOOKING STATUS =====

    public void selectBookingStatus(String status) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        if (status.equalsIgnoreCase("Yes")) {

            WebElement yes = wait.until(
                    ExpectedConditions.presenceOfElementLocated(bookingYesRadio));

            js.executeScript("arguments[0].scrollIntoView(true);", yes);
            js.executeScript("arguments[0].click();", yes);

            if (!yes.isSelected()) {
                throw new RuntimeException("❌ Booking Yes NOT selected");
            }

            System.out.println("✅ Booking Status Selected : YES");

        } else if (status.equalsIgnoreCase("No")) {

            WebElement no = wait.until(
                    ExpectedConditions.presenceOfElementLocated(bookingNoRadio));

            js.executeScript("arguments[0].scrollIntoView(true);", no);
            js.executeScript("arguments[0].click();", no);

            if (!no.isSelected()) {
                throw new RuntimeException("❌ Booking No NOT selected");
            }

            System.out.println("✅ Booking Status Selected : NO");

        } else {

            throw new RuntimeException("❌ Invalid Booking Status : " + status);
        }
    }
    
    public void selectRange(String rangeValue) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(rangeDropdown));

        Select select = new Select(dropdown);

        boolean found = false;

        for (WebElement option : select.getOptions()) {

            if (option.getText().trim()
                    .equalsIgnoreCase(rangeValue)) {

                select.selectByVisibleText(option.getText().trim());

                found = true;
                break;
            }
        }

        if (!found) {

            throw new RuntimeException(
                    "❌ Range NOT found : " + rangeValue);
        }

        System.out.println("✅ Range Selected : " + rangeValue);
    }

    // ===== SPONSOR STATUS =====

    public void selectSponsorStatus(String sponsorStatus) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        if (sponsorStatus.equalsIgnoreCase("Yes")) {

            WebElement yes = wait.until(
                    ExpectedConditions.presenceOfElementLocated(sponsorYesRadio));

            js.executeScript("arguments[0].scrollIntoView(true);", yes);
            js.executeScript("arguments[0].click();", yes);

            if (!yes.isSelected()) {
                throw new RuntimeException("❌ Sponsor Yes NOT selected");
            }

            System.out.println("✅ Sponsor Selected : YES");

        } else if (sponsorStatus.equalsIgnoreCase("No")) {

            WebElement no = wait.until(
                    ExpectedConditions.presenceOfElementLocated(sponsorNoRadio));

            js.executeScript("arguments[0].scrollIntoView(true);", no);
            js.executeScript("arguments[0].click();", no);

            if (!no.isSelected()) {
                throw new RuntimeException("❌ Sponsor No NOT selected");
            }

            System.out.println("✅ Sponsor Selected : NO");

        } else {

            throw new RuntimeException("❌ Invalid Sponsor Status");
        }
    }


    // ===== COMPLETE FLOW =====

    public void fillSpectacleBookingForm(

            String bookingStatus,
            String spectacleType,
            String range,
            String powerRemarks,
            String sponsorStatus,
            String sponsoredBy,
            String reasonForNoBooking,
            String remarks) {

        // ===== BOOKING STATUS =====
        selectBookingStatus(bookingStatus);


        // ===== IF BOOKING YES =====
        if (bookingStatus.equalsIgnoreCase("Yes")) {

    // Spectacle Type
    selectDropdownByText(spectacleTypeDropdown, spectacleType);

    // Range
    selectRange(range);

    // Power Remarks
    WebElement powerRemarkInput = wait.until(
            ExpectedConditions.elementToBeClickable(powerRemarksField));

    powerRemarkInput.clear();
    powerRemarkInput.sendKeys(powerRemarks);

    System.out.println("✅ Power Remarks Entered");

    // Sponsor Status
    selectSponsorStatus(sponsorStatus);

    // Sponsored By
    if (sponsorStatus.equalsIgnoreCase("Yes")) {

        selectDropdownByText(sponsoredByDropdown, sponsoredBy);
    }
}


        // ===== IF BOOKING NO =====
        else if (bookingStatus.equalsIgnoreCase("No")) {

            selectDropdownByText(reasonForNoBookingDropdown, reasonForNoBooking);

            System.out.println("✅ Reason For No Booking Selected");
        }


        // ===== REMARKS =====
        WebElement remarksInput = wait.until(
                ExpectedConditions.elementToBeClickable(remarksField));

        remarksInput.clear();
        remarksInput.sendKeys(remarks);

        System.out.println("✅ Remarks Entered");


        // ===== SAVE =====
        WebElement saveBtn = wait.until(
                ExpectedConditions.elementToBeClickable(saveButton));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", saveBtn);

        saveBtn.click();

        System.out.println("✅ Save Button Clicked");


        // ===== HANDLE POPUP =====
        handleSuccessPopup();

        System.out.println("✅ Spectacle Booking Completed Successfully");
    }


    // ===== SUCCESS POPUP =====

    public void handleSuccessPopup() {

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopup));

        System.out.println("✅ Popup Message : " + popup.getText());

        WebElement okBtn = wait.until(
                ExpectedConditions.elementToBeClickable(okButton));

        okBtn.click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(successPopup));

        System.out.println("✅ Popup Handled Successfully");
    }
}