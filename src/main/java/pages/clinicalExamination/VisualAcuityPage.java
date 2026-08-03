
package pages.clinicalExamination;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;

/** Page object for the Visual Acuity and Refraction sections. */
public class VisualAcuityPage extends BasePage {
	private static final String SELECT2_PREFIX = "s2id_";
	private static final String SELECT2_OPTION_SCRIPT = "var select=document.getElementById(arguments[0]);"
			+ "if(!select) return false;" + "for(var i=0;i<select.options.length;i++){"
			+ " if(select.options[i].text.trim()===arguments[1]){" + "  select.selectedIndex=i;"
			+ "  select.dispatchEvent(new Event('change',{bubbles:true}));"
			+ "  if(window.jQuery){jQuery(select).trigger('change');}" + "  return true;" + " }} return false;";
	private static final String VALID_OPTIONS_SCRIPT = "var select=document.getElementById(arguments[0]), values=[];"
			+ "if(select){for(var i=0;i<select.options.length;i++){" + " var text=select.options[i].text.trim();"
			+ " if(text && text!=='Select' && text!=='--Select--') values.push(text);" + "}} return values;";

	private final Random random = new Random();
	private String selectedSpectaclesOptionValue = "1";

	private final By visualAcuityTab = By.id("visualAcuityRefraction-tab");
	private final By spectaclesDropdown = By.id("CE_ddlVisualActuityWearingSpecs");
	private final By specAgeYears = By.id("CE_txtUsingSpectaclesYear");
	private final By specAgeMonths = By.id("CE_txtUsingSpectaclesMonth");
	private final By purposeField = By.id("CE_txtVisualAcuitySpecsPrupose");

	private final By reUnaided = By.id("s2id_CE_ddl_RE_Unaided");
	private final By reWithSpecs = By.id("s2id_CE_ddl_RE_WithSpecs");
	private final By reWithPH = By.id("s2id_CE_ddl_RE_WithPH");
	private final By reNearVision = By.id("s2id_CE_ddl_RE_NearVision");
	private final By leUnaided = By.id("s2id_CE_ddl_LE_Unaided");
	private final By leWithSpecs = By.id("s2id_CE_ddl_LE_WithSpecs");
	private final By leWithPH = By.id("s2id_CE_ddl_LE_WithPH");
	private final By leNearVision = By.id("s2id_CE_ddl_LE_NearVision");

	private final By saveButton = By.id("CE_btnAddUpadateVisualAcuity");
	private final By contrastField = By.id("CECV_txtContrastTest");
	private final By otherSaveButton = By.id("CE_btnAddUpdateColorVision");
	private final By successPopup = By.id("popup_message");
	private final By okButton = By.id("popup_ok");

	private final By rePGSph = By.id("s2id_CE_ddlRefraction_RE_PGP_SPH");
	private final By rePGCyl = By.id("s2id_CE_ddlRefraction_RE_PGP_CYL");
	private final By rePGAxis = By.id("s2id_CE_ddlRefraction_RE_PGP_Axis");
	private final By rePGAdd = By.id("s2id_CE_ddlRefraction_RE_PGP_Add");
	private final By reFinalSph = By.id("s2id_CE_ddlRefraction_RE_Acceptance_SPH");
	private final By reFinalCyl = By.id("s2id_CE_ddlRefraction_RE_Acceptance_CYL");
	private final By reFinalAxis = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Axis");
	private final By reFinalAdd = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Add");
	private final By lePGSph = By.id("s2id_CE_ddlRefraction_LE_PGP_SPH");
	private final By lePGCyl = By.id("s2id_CE_ddlRefraction_LE_PGP_CYL");
	private final By lePGAxis = By.id("s2id_CE_ddlRefraction_LE_PGP_Axis");
	private final By lePGAdd = By.id("s2id_CE_ddlRefraction_LE_PGP_Add");
	private final By leFinalSph = By.id("s2id_CE_ddlRefraction_LE_Acceptance_SPH");
	private final By leFinalCyl = By.id("s2id_CE_ddlRefraction_LE_Acceptance_CYL");
	private final By leFinalAxis = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Axis");
	private final By leFinalAdd = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Add");
	private final By refractionSaveBtn = By.id("CE_btnAddUpadateRefraction");

	private final By reDrySph = By.id("s2id_CE_ddlRefraction_RE_DRY_SPH");
	private final By reDryCyl = By.id("s2id_CE_ddlRefraction_RE_DRY_CYL");
	private final By reDryAxis = By.id("s2id_CE_ddlRefraction_RE_DRY_Axis");
	private final By leDrySph = By.id("s2id_CE_ddlRefraction_LE_DRY_SPH");
	private final By leDryCyl = By.id("s2id_CE_ddlRefraction_LE_DRY_CYL");
	private final By leDryAxis = By.id("s2id_CE_ddlRefraction_LE_DRY_Axis");
	private final By reDryRemark = By.id("CE_txtRefraction_DRY_Remarks_RE");
	private final By leDryRemark = By.id("CE_txtRefraction_DRY_Remarks_LE");
	private final By reCycloSphVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_SPH");
	private final By reCycloCylVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_CYL");
	private final By reCycloAxisVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_Axis");
	private final By leCycloSphVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_SPH");
	private final By leCycloCylVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_CYL");
	private final By leCycloAxisVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_Axis");
	private final By reCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_RE");
	private final By leCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_LE");
	private final By reAddExtra = By.id("s2id_CE_ddlRefraction_RE_ADD_Add");
	private final By leAddExtra = By.id("s2id_CE_ddlRefraction_LE_ADD_Add");
	private final By reAddRemark = By.id("CE_txtRefraction_IOP_Remarks_RE");
	private final By leAddRemark = By.id("CE_txtRefraction_IOP_Remarks_LE");
	private final By reNearVisionExtra = By.id("s2id_CE_ddlRefraction_RE_NearVision_SPH");
	private final By leNearVisionExtra = By.id("s2id_CE_ddlRefraction_LE_NearVision_SPH");
	private final By reNearNpc = By.id("s2id_CE_ddlRefraction_RE_NearVision_CYL");
	private final By leNearNpc = By.id("s2id_CE_ddlRefraction_LE_NearVision_CYL");
	private final By reIOPValueDropdown = By.id("s2id_CE_txtRefraction_RE_IOPValue");
	private final By leIOPValueDropdown = By.id("s2id_CE_txtRefraction_LE_IOPValue");

	public VisualAcuityPage(WebDriver driver) {
		super(driver);
	}

	public void openVisualAcuityTab() {
		WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(visualAcuityTab));
		if (!tab.getAttribute("class").contains("active"))
			scrollAndClick(tab);
	}

	public void selectSpectacles(String value) {
		String normalized = value == null ? "" : value.trim();
		selectedSpectaclesOptionValue = "yes".equalsIgnoreCase(normalized) ? "1"
				: "no".equalsIgnoreCase(normalized) ? "0" : "-1";
		forceSelectSpectaclesDropdown();
	}

	public void enterSpectacleAge(String years, String months) {
		typeText(specAgeYears, years);
		typeText(specAgeMonths, months);
	}

	public void enterPurpose(String purpose) {
		typeText(purposeField, purpose);
	}

	public void fillRightEye(String unaided, String withSpecs, String withPH, String near) {
		selectValues(new By[] { reUnaided, reWithSpecs, reWithPH, reNearVision }, unaided, withSpecs, withPH, near);
	}

	public void fillLeftEye(String unaided, String withSpecs, String withPH, String near) {
		selectValues(new By[] { leUnaided, leWithSpecs, leWithPH, leNearVision }, unaided, withSpecs, withPH, near);
	}

	public void fillRightEyeRandom() {
		selectRandomValues(reUnaided, reWithSpecs, reWithPH, reNearVision);
	}

	public void fillLeftEyeRandom() {
		selectRandomValues(leUnaided, leWithSpecs, leWithPH, leNearVision);
	}

	private void selectSelect2Dropdown(By containerLocator, String value) {
		if (isBlank(value))
			return;
		WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(containerLocator));
		Boolean selected = (Boolean) js().executeScript(SELECT2_OPTION_SCRIPT, selectId(container), value.trim());
		if (!Boolean.TRUE.equals(selected))
			throw new IllegalArgumentException("Option not found: " + value);
	}

	@SuppressWarnings("unchecked")
	private void selectRandomSelect2Dropdown(By containerLocator) {
		WebElement container = wait.until(ExpectedConditions.elementToBeClickable(containerLocator));
		List<String> options = (List<String>) js().executeScript(VALID_OPTIONS_SCRIPT, selectId(container));
		if (options == null || options.isEmpty())
			throw new IllegalStateException("No selectable values for " + containerLocator);
		selectSelect2Dropdown(containerLocator, options.get(random.nextInt(options.size())));
	}

	public void clickSave() {
		forceSelectSpectaclesDropdown();
		scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(saveButton)));
	}

	public void clickOtherSave() {
		scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(otherSaveButton)));
	}

	public void clickRefractionSave() {
		scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(refractionSaveBtn)));
	}

	public void handleSuccessPopup() {
		Object confirmation = wait.until(d -> {
			try {
				return d.switchTo().alert();
			} catch (NoAlertPresentException ignored) {
				List<WebElement> popups = d.findElements(successPopup);
				return !popups.isEmpty() && popups.get(0).isDisplayed() ? popups.get(0) : null;
			}
		});
		String message;
		if (confirmation instanceof Alert) {
			Alert alert = (Alert) confirmation;
			message = alert.getText().trim();
			alert.accept();
		} else {
			message = ((WebElement) confirmation).getText().trim();
			scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(okButton)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
		}
		if (message.toLowerCase().contains("must save visual acuity"))
			throw new IllegalStateException("Visual Acuity was not saved before refraction: " + message);
	}

	public void selectColorVision(String value) {
		String id = "normal".equalsIgnoreCase(value) ? "CECV_rdbNormal"
				: "abnormal".equalsIgnoreCase(value) ? "CECV_rdbAbnormal" : "CECV_rdbPartial";
		selectRadio(By.id(id));
	}

	public void selectStereopsis(String value) {
		String id = "<40".equals(value) ? "CECV_rdbStereopsis1"
				: "50-100".equals(value) ? "CECV_rdbStereopsis2" : "CECV_rdbStereopsis3";
		selectRadio(By.id(id));
	}

	public void enterContrast(String value) {
		typeText(contrastField, value);
		
	}

	public void selectRandomColorVision() {
		selectColorVision(randomItem("Normal", "Abnormal", "CVD"));
	}

	public void selectRandomStereopsis() {
		selectStereopsis(randomItem("<40", "50-100", ">100"));
	}

	public void enterRandomContrast() {
	    enterContrast(String.valueOf(random.nextInt(91) + 10));
	}

	public void fillDryRetinoscopy(String reRemark, String leRemark) {
		typeText(reDryRemark, reRemark);
		typeText(leDryRemark, leRemark);
	}

	public void fillCycloplegic(String reRemark, String leRemark) {
		typeText(reCycloRemark, reRemark);
		typeText(leCycloRemark, leRemark);
	}

	public void fillAddRemark(String reRemark, String leRemark) {
		typeText(reAddRemark, reRemark);
		typeText(leAddRemark, leRemark);
	}

	public void fillIOP(String reValue, String leValue, String time) {
		selectIOPValue(reValue, leValue);
	}

	public void fillRefractionRightEye(String sph, String cyl, String axis, String add, String finalSph,
			String finalCyl, String finalAxis, String finalAdd) {
		selectValues(new By[] { rePGSph, rePGCyl, rePGAxis, rePGAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd },
				sph, cyl, axis, add, finalSph, finalCyl, finalAxis, finalAdd);
	}

	public void fillRefractionLeftEye(String sph, String cyl, String axis, String add, String finalSph, String finalCyl,
			String finalAxis, String finalAdd) {
		selectValues(new By[] { lePGSph, lePGCyl, lePGAxis, lePGAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd },
				sph, cyl, axis, add, finalSph, finalCyl, finalAxis, finalAdd);
	}

	public void fillDryRetinoscopyValues(String reSph, String reCyl, String reAxis, String leSph, String leCyl,
			String leAxis) {
		selectValues(new By[] { reDrySph, reDryCyl, reDryAxis, leDrySph, leDryCyl, leDryAxis }, reSph, reCyl, reAxis,
				leSph, leCyl, leAxis);
	}

	public void fillCycloplegicValues(String reSph, String reCyl, String reAxis, String leSph, String leCyl,
			String leAxis) {
		selectValues(
				new By[] { reCycloSphVal, reCycloCylVal, reCycloAxisVal, leCycloSphVal, leCycloCylVal, leCycloAxisVal },
				reSph, reCyl, reAxis, leSph, leCyl, leAxis);
	}

	public void fillAdditionalAdd(String reAdd, String leAdd) {
		selectValues(new By[] { reAddExtra, leAddExtra }, reAdd, leAdd);
	}

	public void fillNearVisionExtra(String reVal, String leVal) {
		selectValues(new By[] { reNearVisionExtra, leNearVisionExtra }, reVal, leVal);
	}

	public void selectIOPValue(String reValue, String leValue) {
		selectValues(new By[] { reIOPValueDropdown, leIOPValueDropdown }, reValue, leValue);
	}

	public void fillVisualAcuityForm(String spectacles, String years, String months, String purpose,
			String reUnaidedVal, String reSpecs, String rePH, String reNear, String leUnaidedVal, String leSpecs,
			String lePH, String leNear, String colorVision, String stereopsis, String contrast) {
		openVisualAcuityTab();
		selectSpectacles(spectacles);
		enterSpectacleAge(years, months);
		enterPurpose(purpose);
		if (allBlank(reUnaidedVal, reSpecs, rePH, reNear))
			fillRightEyeRandom();
		else
			fillRightEye(reUnaidedVal, reSpecs, rePH, reNear);
		if (allBlank(leUnaidedVal, leSpecs, lePH, leNear))
			fillLeftEyeRandom();
		else
			fillLeftEye(leUnaidedVal, leSpecs, lePH, leNear);
		clickSave();
		handleSuccessPopup();
		waitForVisualAcuitySaveComplete();
		if (isBlank(colorVision))
			selectRandomColorVision();
		else
			selectColorVision(colorVision);
		if (isBlank(stereopsis))
			selectRandomStereopsis();
		else
			selectStereopsis(stereopsis);
		if (isBlank(contrast))
			enterRandomContrast();
		else
			enterContrast(contrast);
		clickOtherSave();
		handleSuccessPopup();
		waitForRefractionSection();
	}

	public void fillRefractionForm(String reSph, String reCyl, String reAxis, String reAdd, String reFinalSph,
			String reFinalCyl, String reFinalAxis, String reFinalAdd, String leSph, String leCyl, String leAxis,
			String leAdd, String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd) {
		fillPgAndFinal(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd, leSph, leCyl,
				leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
		clickRefractionSave();
		handleSuccessPopup();
	}

	public void fillAdditionalRefractionDetails(String reDry, String leDry, String reCyclo, String leCyclo,
			String reAddRem, String leAddRem, String reNpc, String leNpc, String reIOPVal, String leIOPVal,
			String time) {
		fillDryRetinoscopy(reDry, leDry);
		fillCycloplegic(reCyclo, leCyclo);
		fillAddRemark(reAddRem, leAddRem);
		fillNearVisionExtra(reNpc, leNpc);
		fillIOP(reIOPVal, leIOPVal, time);
		enterCycloplegicRemark("Test");
	}

	public void fillCompleteRefraction(String reSph, String reCyl, String reAxis, String reAdd, String leSph,
			String leCyl, String leAxis, String leAdd, String reDrySphVal, String reDryCylVal, String reDryAxisVal,
			String leDrySphVal, String leDryCylVal, String leDryAxisVal, String reCycloSph, String reCycloCyl,
			String reCycloAxis, String leCycloSph, String leCycloCyl, String leCycloAxis, String reFinalSph,
			String reFinalCyl, String reFinalAxis, String reFinalAdd, String leFinalSph, String leFinalCyl,
			String leFinalAxis, String leFinalAdd, String reAddExtraVal, String leAddExtraVal) {
		fillPgAndFinal(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd, leSph, leCyl,
				leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
		if (isBlank(reDrySphVal))
			fillDryRetinoscopyRandom();
		else
			fillDryRetinoscopyValues(reDrySphVal, reDryCylVal, reDryAxisVal, leDrySphVal, leDryCylVal, leDryAxisVal);
		if (isBlank(reCycloSph))
			fillCycloplegicRandom();
		else
			fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis, leCycloSph, leCycloCyl, leCycloAxis);
		if (isBlank(reAddExtraVal))
			fillAdditionalAddRandom();
		else
			fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
		clickRefractionSave();
		handleSuccessPopup();
	}

	public void fillFullRefractionFlow(String reSph, String reCyl, String reAxis, String reAdd, String leSph,
			String leCyl, String leAxis, String leAdd, String reDrySph, String reDryCyl, String reDryAxis,
			String leDrySph, String leDryCyl, String leDryAxis, String reCycloSph, String reCycloCyl,
			String reCycloAxis, String leCycloSph, String leCycloCyl, String leCycloAxis, String reFinalSph,
			String reFinalCyl, String reFinalAxis, String reFinalAdd, String leFinalSph, String leFinalCyl,
			String leFinalAxis, String leFinalAdd, String reAddExtraVal, String leAddExtraVal, String reDryRemarkVal,
			String leDryRemarkVal, String reCycloRemarkVal, String leCycloRemarkVal, String reAddRemarkVal,
			String leAddRemarkVal, String reNpc, String leNpc, String reIOP, String leIOP, String iopTimeVal) {
		waitForRefractionSection();
		fillPgAndFinal(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd, leSph, leCyl,
				leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
		if (isBlank(reDrySph))
			fillDryRetinoscopyRandom();
		else
			fillDryRetinoscopyValues(reDrySph, reDryCyl, reDryAxis, leDrySph, leDryCyl, leDryAxis);
		fillDryRetinoscopy(reDryRemarkVal, leDryRemarkVal);
		if (isBlank(reCycloSph))
			fillCycloplegicRandom();
		else
			fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis, leCycloSph, leCycloCyl, leCycloAxis);
		fillCycloplegic(reCycloRemarkVal, leCycloRemarkVal);
		if (isBlank(reAddExtraVal))
			fillAdditionalAddRandom();
		else
			fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
		fillAddRemark(reAddRemarkVal, leAddRemarkVal);
		if (isBlank(reNpc))
			fillNearVisionExtraRandom();
		else
			fillNearVisionExtra(reNpc, leNpc);
		if (isBlank(reIOP))
			selectRandomIOPValue();
		else
			fillIOP(reIOP, leIOP, iopTimeVal);
		waitForAjaxToFinish();
		clickRefractionSave();
		handleSuccessPopup();
	}

	public void fillRefractionRightEyeRandom() {
		selectRandomValues(rePGSph, rePGCyl, rePGAxis, rePGAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
	}

	public void fillRefractionLeftEyeRandom() {
		selectRandomValues(lePGSph, lePGCyl, lePGAxis, lePGAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
	}

	public void fillDryRetinoscopyRandom() {
		selectRandomValues(reDrySph, reDryCyl, reDryAxis, leDrySph, leDryCyl, leDryAxis);
	}

	public void fillCycloplegicRandom() {
		selectRandomValues(reCycloSphVal, reCycloCylVal, reCycloAxisVal, leCycloSphVal, leCycloCylVal, leCycloAxisVal);
	}

	public void fillAdditionalAddRandom() {
		selectRandomValues(reAddExtra, leAddExtra);
	}

	public void fillNearVisionExtraRandom() {
		selectRandomValues(reNearVisionExtra, leNearVisionExtra);
	}

	public void selectRandomIOPValue() {
		selectRandomValues(reIOPValueDropdown, leIOPValueDropdown);
	}

	private void fillPgAndFinal(String reSph, String reCyl, String reAxis, String reAdd, String reFinalSph,
			String reFinalCyl, String reFinalAxis, String reFinalAdd, String leSph, String leCyl, String leAxis,
			String leAdd, String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd) {
		if (isBlank(reSph))
			fillRefractionRightEyeRandom();
		else
			fillRefractionRightEye(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
		if (isBlank(leSph))
			fillRefractionLeftEyeRandom();
		else
			fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
	}

	private void waitForVisualAcuitySaveComplete() {
		waitForAjaxToFinish();
	}

	private void waitForRefractionSection() {
		waitForAjaxToFinish();
		wait.until(ExpectedConditions.elementToBeClickable(rePGSph));
	}

	private void waitForAjaxToFinish() {
		wait.until(d -> Boolean.TRUE.equals(js()
				.executeScript("return document.readyState==='complete' && (!window.jQuery || jQuery.active===0);")));
	}

	private void enterCycloplegicRemark(String value) {
		WebElement element = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.id("CE_txtCycloplegic_RE_Remark")));
		js().executeScript("arguments[0].removeAttribute('disabled');arguments[0].removeAttribute('readonly');"
				+ "arguments[0].value=arguments[1];arguments[0].dispatchEvent(new Event('input',{bubbles:true}));"
				+ "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));", element, value);
	}

	private void forceSelectSpectaclesDropdown() {
		WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(spectaclesDropdown));
		String selected = (String) js().executeScript(
				"var s=arguments[0],v=arguments[1];s.removeAttribute('disabled');s.value=v;"
						+ "s.dispatchEvent(new Event('input',{bubbles:true}));s.dispatchEvent(new Event('change',{bubbles:true}));"
						+ "if(window.jQuery){jQuery(s).val(v).trigger('change');}return s.value;",
				dropdown, selectedSpectaclesOptionValue);
		if (!selectedSpectaclesOptionValue.equals(selected))
			throw new IllegalStateException("Spectacles option was not selected: " + selectedSpectaclesOptionValue);
	}

	private void selectValues(By[] locators, String... values) {
		for (int i = 0; i < locators.length; i++)
			selectSelect2Dropdown(locators[i], values[i]);
	}

	private void selectRandomValues(By... locators) {
		for (By locator : locators)
			selectRandomSelect2Dropdown(locator);
	}

	private void selectRadio(By locator) {
		WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		js().executeScript("arguments[0].checked=true;arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
				radio);
	}

	private void typeText(By locator, String value) {
		if (value == null)
			return;
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		clearAndType(element, value);
	}

	private void scrollAndClick(WebElement element) {
		scrollToElement(element);
		js().executeScript("arguments[0].click();", element);
	}

	private JavascriptExecutor js() {
		return (JavascriptExecutor) driver;
	}

	private String selectId(WebElement container) {
		String id = container.getAttribute("id");
		if (id == null || !id.startsWith(SELECT2_PREFIX))
			throw new IllegalArgumentException("Expected a Select2 container: " + id);
		return id.substring(SELECT2_PREFIX.length());
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private boolean allBlank(String... values) {
		for (String value : values)
			if (!isBlank(value))
				return false;
		return true;
	}

	private String randomItem(String... values) {
		return values[random.nextInt(values.length)];
	}
}


















// ﻿package pages.clinicalExamination;

// import pages.BasePage;

// import org.openqa.selenium.*;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.Select;

// public class VisualAcuityPage extends BasePage {

//     public VisualAcuityPage(WebDriver driver) {
//         super(driver);
//     }

//     // ===== TAB =====
//     private By visualAcuityTab = By.id("visualAcuityRefraction-tab");

//     // ===== TOP SECTION =====
//     private By spectaclesDropdown = By.id("CE_ddlVisualActuityWearingSpecs");
//     private By specAgeYears = By.id("CE_txtUsingSpectaclesYear");
//     private By specAgeMonths = By.id("CE_txtUsingSpectaclesMonth");
//     private By purposeField = By.id("CE_txtVisualAcuitySpecsPrupose");
//     private String selectedSpectaclesOptionValue = "1";

//     // ===== RIGHT EYE =====
//     private By reUnaided = By.id("s2id_CE_ddl_RE_Unaided");
//     private By reWithSpecs = By.id("s2id_CE_ddl_RE_WithSpecs");
//     private By reWithPH = By.id("s2id_CE_ddl_RE_WithPH");
//     private By reNearVision = By.id("s2id_CE_ddl_RE_NearVision");

//     // ===== LEFT EYE =====
//     private By leUnaided = By.id("s2id_CE_ddl_LE_Unaided");
//     private By leWithSpecs = By.id("s2id_CE_ddl_LE_WithSpecs");
//     private By leWithPH = By.id("s2id_CE_ddl_LE_WithPH");
//     private By leNearVision = By.id("s2id_CE_ddl_LE_NearVision");

//     // ===== SAVE BUTTON =====
//     private By saveButton = By.id("CE_btnAddUpadateVisualAcuity");

//     // ===== OTHER DIAGNOSTIC =====
//     private By contrastField = By.id("CECV_txtContrastTest");
//     private By otherSaveButton = By.id("CE_btnAddUpdateColorVision");

//     // ===== POPUP =====
//     private By successPopup = By.id("popup_message");
//     private By okButton = By.id("popup_ok");

//     // ===== REFRACTION TAB =====
//     private By rePGSph = By.id("s2id_CE_ddlRefraction_RE_PGP_SPH");
//     private By rePGCyl = By.id("s2id_CE_ddlRefraction_RE_PGP_CYL");
//     private By rePGAxis = By.id("s2id_CE_ddlRefraction_RE_PGP_Axis");
//     private By rePGAdd = By.id("s2id_CE_ddlRefraction_RE_PGP_Add");

//     private By reFinalSph = By.id("s2id_CE_ddlRefraction_RE_Acceptance_SPH");
//     private By reFinalCyl = By.id("s2id_CE_ddlRefraction_RE_Acceptance_CYL");
//     private By reFinalAxis = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Axis");
//     private By reFinalAdd = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Add");

//     private By lePGSph = By.id("s2id_CE_ddlRefraction_LE_PGP_SPH");
//     private By lePGCyl = By.id("s2id_CE_ddlRefraction_LE_PGP_CYL");
//     private By lePGAxis = By.id("s2id_CE_ddlRefraction_LE_PGP_Axis");
//     private By lePGAdd = By.id("s2id_CE_ddlRefraction_LE_PGP_Add");

//     private By leFinalSph = By.id("s2id_CE_ddlRefraction_LE_Acceptance_SPH");
//     private By leFinalCyl = By.id("s2id_CE_ddlRefraction_LE_Acceptance_CYL");
//     private By leFinalAxis = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Axis");
//     private By leFinalAdd = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Add");

//     private By refractionSaveBtn = By.id("CE_btnAddUpadateRefraction");

//     // ===== DRY RETINOSCOPY VALUES =====
//     private By reDrySph = By.id("s2id_CE_ddlRefraction_RE_DRY_SPH");
//     private By reDryCyl = By.id("s2id_CE_ddlRefraction_RE_DRY_CYL");
//     private By reDryAxis = By.id("s2id_CE_ddlRefraction_RE_DRY_Axis");

//     private By leDrySph = By.id("s2id_CE_ddlRefraction_LE_DRY_SPH");
//     private By leDryCyl = By.id("s2id_CE_ddlRefraction_LE_DRY_CYL");
//     private By leDryAxis = By.id("s2id_CE_ddlRefraction_LE_DRY_Axis");

//     // ===== DRY RETINOSCOPY REMARK =====
//     private By reDryRemark = By.id("CE_txtRefraction_DRY_Remarks_RE");
//     private By leDryRemark = By.id("CE_txtRefraction_DRY_Remarks_LE");

//     // ===== CYCLOPLEGIC VALUES =====
//     private By reCycloSphVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_SPH");
//     private By reCycloCylVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_CYL");
//     private By reCycloAxisVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_Axis");

//     private By leCycloSphVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_SPH");
//     private By leCycloCylVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_CYL");
//     private By leCycloAxisVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_Axis");

//     // ===== EXTRA ADD =====
//     private By reAddExtra = By.id("s2id_CE_ddlRefraction_RE_ADD_Add");
//     private By leAddExtra = By.id("s2id_CE_ddlRefraction_LE_ADD_Add");

//     // ===== NEAR VISION EXTRA =====
//     private By reNearVisionExtra = By.id("s2id_CE_ddlRefraction_RE_NearVision_SPH");
//     private By leNearVisionExtra = By.id("s2id_CE_ddlRefraction_LE_NearVision_SPH");

//     // ===== IOP VALUE DROPDOWN =====
//     private By reIOPValueDropdown = By.id("s2id_CE_txtRefraction_RE_IOPValue");
//     private By leIOPValueDropdown = By.id("s2id_CE_txtRefraction_LE_IOPValue");

//     // ===== CYCLOPLEGIC REMARK =====
//     private By reCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_RE");
//     private By leCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_LE");

//     // ===== ADD REMARK =====
//     private By reAddRemark = By.id("CE_txtRefraction_IOP_Remarks_RE");
//     private By leAddRemark = By.id("CE_txtRefraction_IOP_Remarks_LE");

//     // ===== NEAR VISION / NPC =====
//     private By reNearNpc = By.id("s2id_CE_ddlRefraction_RE_NearVision_CYL");
//     private By leNearNpc = By.id("s2id_CE_ddlRefraction_LE_NearVision_CYL");

//     // ===== TAB ACTION =====
//     public void openVisualAcuityTab() {
//         WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(visualAcuityTab));
//         if (!tab.getAttribute("class").contains("active")) {
//             scrollAndClick(tab);
//         }
//     }

//     // ===== TOP SECTION METHODS =====
//     public void selectSpectacles(String value) {
//         String optionText = value.equalsIgnoreCase("yes") ? "Yes"
//                 : value.equalsIgnoreCase("no") ? "No"
//                 : "Select";
//         selectedSpectaclesOptionValue = optionText.equals("Yes") ? "1"
//                 : optionText.equals("No") ? "0"
//                 : "-1";

//         forceSelectSpectaclesDropdown();
//         System.out.println("Spectacles selected: " + optionText + " (" + selectedSpectaclesOptionValue + ")");
//     }
//     public void enterSpectacleAge(String years, String months) {
//         clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeYears)), years);
//         clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeMonths)), months);
//     }

//     public void enterPurpose(String purpose) {
//         WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(purposeField));
//         input.clear();
//         input.sendKeys(purpose);
//     }

//     // ===== RE + LE DROPDOWN METHODS =====
//     public void fillRightEye(String unaided, String withSpecs, String withPH, String near) {
//         selectSelect2Dropdown(reUnaided, unaided);
//         selectSelect2Dropdown(reWithSpecs, withSpecs);
//         selectSelect2Dropdown(reWithPH, withPH);
//         selectSelect2Dropdown(reNearVision, near);
//     }

//     public void fillLeftEye(String unaided, String withSpecs, String withPH, String near) {
//         selectSelect2Dropdown(leUnaided, unaided);
//         selectSelect2Dropdown(leWithSpecs, withSpecs);
//         selectSelect2Dropdown(leWithPH, withPH);
//         selectSelect2Dropdown(leNearVision, near);
//     }

//     // ===== SELECT2 UNIVERSAL METHOD =====
//     private void selectSelect2Dropdown(By select2Container, String value) {
//         if (value == null || value.trim().isEmpty()) {
//             return;
//         }

//         WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(select2Container));

//         String select2Id = container.getAttribute("id");
//         String actualSelectId = select2Id.replace("s2id_", "");

//         JavascriptExecutor js = (JavascriptExecutor) driver;

//         js.executeScript(
//                 "var select = document.getElementById(arguments[0]);" +
//                 "if (!select) return false;" +
//                 "for (var i = 0; i < select.options.length; i++) {" +
//                 "  var txt = select.options[i].text.trim();" +
//                 "  if (txt === arguments[1]) {" +
//                 "    select.selectedIndex = i;" +
//                 "    select.dispatchEvent(new Event('change', { bubbles: true }));" +
//                 "    if (window.jQuery) { jQuery(select).trigger('change'); }" +
//                 "    return true;" +
//                 "  }" +
//                 "}" +
//                 "return false;",
//                 actualSelectId, value
//         );

//         System.out.println("Selected: " + value);
//     }

//     // ===== SAVE =====
//     public void clickSave() {
//         forceSelectSpectaclesDropdown();
//         WebElement save = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
//         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", save);
//         wait.until(ExpectedConditions.elementToBeClickable(save));

//         ((JavascriptExecutor) driver).executeScript(
//                 "var select = document.getElementById('CE_ddlVisualActuityWearingSpecs');" +
//                 "var val = arguments[1];" +
//                 "if (select) {" +
//                 "  select.removeAttribute('disabled');" +
//                 "  select.value = val;" +
//                 "  for (var i = 0; i < select.options.length; i++) {" +
//                 "    select.options[i].selected = (select.options[i].value === val);" +
//                 "  }" +
//                 "  select.dispatchEvent(new Event('input', { bubbles: true }));" +
//                 "  select.dispatchEvent(new Event('change', { bubbles: true }));" +
//                 "  select.value = val;" +
//                 "  for (var j = 0; j < select.options.length; j++) {" +
//                 "    select.options[j].selected = (select.options[j].value === val);" +
//                 "  }" +
//                 "}" +
//                 "arguments[0].click();",
//                 save, selectedSpectaclesOptionValue
//         );

//         System.out.println("Visual Acuity save clicked with spectacles value: " + selectedSpectaclesOptionValue);
//     }
//     public void clickOtherSave() {
//         scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(otherSaveButton)));
//     }

//     public void clickRefractionSave() {
//         scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(refractionSaveBtn)));
//     }

//     // ===== POPUP / ALERT HANDLER =====
//     public void handleSuccessPopup() {
//         Object confirmation = wait.until(d -> {
//             try {
//                 return d.switchTo().alert();
//             } catch (NoAlertPresentException ignored) {
//                 java.util.List<WebElement> popups = d.findElements(successPopup);
//                 if (!popups.isEmpty() && popups.get(0).isDisplayed()) {
//                     return popups.get(0);
//                 }
//                 return null;
//             }
//         });

//         if (confirmation instanceof Alert) {
//             Alert alert = (Alert) confirmation;
//             String message = alert.getText().trim();
//             System.out.println("Alert: " + message);
//             alert.accept();

//             if (message.toLowerCase().contains("must save visual acuity")) {
//                 throw new RuntimeException("Visual Acuity was not saved before refraction. Alert: " + message);
//             }
//             return;
//         }

//         WebElement popup = (WebElement) confirmation;
//         String message = popup.getText().trim();
//         System.out.println("Popup: " + message);

//         WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
//         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);

//         wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));

//         if (message.toLowerCase().contains("must save visual acuity")) {
//             throw new RuntimeException("Visual Acuity was not saved before refraction. Popup: " + message);
//         }
//     }
//     // ===== OTHER DIAGNOSTIC =====
//     public void selectColorVision(String value) {
//         String id = value.equalsIgnoreCase("normal") ? "CECV_rdbNormal"
//                 : value.equalsIgnoreCase("abnormal") ? "CECV_rdbAbnormal"
//                 : "CECV_rdbPartial";

//         WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
//         ((JavascriptExecutor) driver).executeScript(
//                 "arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
//                 radio
//         );

//         System.out.println("Color vision selected: " + value);
//     }

//     public void selectStereopsis(String value) {
//         String id = value.equals("<40") ? "CECV_rdbStereopsis1"
//                 : value.equals("50-100") ? "CECV_rdbStereopsis2"
//                 : "CECV_rdbStereopsis3";

//         WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
//         ((JavascriptExecutor) driver).executeScript(
//                 "arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
//                 radio
//         );

//         System.out.println("Stereopsis selected: " + value);
//     }

//     public void enterContrast(String value) {
//         WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(contrastField));
//         input.click();
//         input.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
//         ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", input);
//         input.sendKeys(value);

//         System.out.println("Contrast entered: " + value);
//     }

//     // ===== UTIL =====
//     private void scrollAndClick(WebElement element) {
//         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
//         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//     }

//     protected void click(By locator) {
//         wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
//     }

//     private void forceSelectSpectaclesDropdown() {
//         WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(spectaclesDropdown));
//         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);

//         JavascriptExecutor js = (JavascriptExecutor) driver;
//         String selectedValue = (String) js.executeScript(
//                 "var select = arguments[0];" +
//                 "var val = arguments[1];" +
//                 "select.removeAttribute('disabled');" +
//                 "select.value = val;" +
//                 "for (var i = 0; i < select.options.length; i++) {" +
//                 "  select.options[i].selected = (select.options[i].value === val);" +
//                 "}" +
//                 "select.dispatchEvent(new Event('input', { bubbles: true }));" +
//                 "select.dispatchEvent(new Event('change', { bubbles: true }));" +
//                 "select.dispatchEvent(new Event('blur', { bubbles: true }));" +
//                 "if (window.jQuery) { jQuery(select).val(val).trigger('change').trigger('blur'); }" +
//                 "select.value = val;" +
//                 "for (var j = 0; j < select.options.length; j++) {" +
//                 "  select.options[j].selected = (select.options[j].value === val);" +
//                 "}" +
//                 "return select.value;",
//                 dropdown, selectedSpectaclesOptionValue
//         );

//         if (!selectedSpectaclesOptionValue.equals(selectedValue)) {
//             throw new RuntimeException("Presently With Spectacles / Contact Lens dropdown not selected. Expected: "
//                     + selectedSpectaclesOptionValue + ", Actual: " + selectedValue);
//         }

//         System.out.println("Verified spectacles dropdown value before save: " + selectedValue);
//     }
//     private void enterText(By locator, String value) {
//         WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
//         element.click();
//         element.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
//         ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", element);
//         element.sendKeys(value);

//         System.out.println("Entered: " + value);
//     }

//     // ===== REMARK METHODS =====
//     public void fillDryRetinoscopy(String reRemark, String leRemark) {
//         enterText(reDryRemark, reRemark);
//         enterText(leDryRemark, leRemark);
//     }

//     public void fillCycloplegic(String reRemark, String leRemark) {
//         enterText(reCycloRemark, reRemark);
//         enterText(leCycloRemark, leRemark);
//     }

//     public void fillAddRemark(String reRemark, String leRemark) {
//         enterText(reAddRemark, reRemark);
//         enterText(leAddRemark, leRemark);
//     }

//     public void fillIOP(String reValue, String leValue, String time) {
//         selectIOPValue(reValue, leValue);
//     }

//     // ===== RE + LE REFRACTION METHODS =====
//     public void fillRefractionRightEye(String sph, String cyl, String axis, String add,
//                                        String finalSph, String finalCyl, String finalAxis, String finalAdd) {
//         selectSelect2Dropdown(rePGSph, sph);
//         selectSelect2Dropdown(rePGCyl, cyl);
//         selectSelect2Dropdown(rePGAxis, axis);
//         selectSelect2Dropdown(rePGAdd, add);

//         selectSelect2Dropdown(reFinalSph, finalSph);
//         selectSelect2Dropdown(reFinalCyl, finalCyl);
//         selectSelect2Dropdown(reFinalAxis, finalAxis);
//         selectSelect2Dropdown(reFinalAdd, finalAdd);
//     }

//     public void fillRefractionLeftEye(String sph, String cyl, String axis, String add,
//                                       String finalSph, String finalCyl, String finalAxis, String finalAdd) {
//         selectSelect2Dropdown(lePGSph, sph);
//         selectSelect2Dropdown(lePGCyl, cyl);
//         selectSelect2Dropdown(lePGAxis, axis);
//         selectSelect2Dropdown(lePGAdd, add);

//         selectSelect2Dropdown(leFinalSph, finalSph);
//         selectSelect2Dropdown(leFinalCyl, finalCyl);
//         selectSelect2Dropdown(leFinalAxis, finalAxis);
//         selectSelect2Dropdown(leFinalAdd, finalAdd);
//     }

//     public void fillDryRetinoscopyValues(String reSph, String reCyl, String reAxis,
//                                          String leSph, String leCyl, String leAxis) {
//         selectSelect2Dropdown(reDrySph, reSph);
//         selectSelect2Dropdown(reDryCyl, reCyl);
//         selectSelect2Dropdown(reDryAxis, reAxis);

//         selectSelect2Dropdown(leDrySph, leSph);
//         selectSelect2Dropdown(leDryCyl, leCyl);
//         selectSelect2Dropdown(leDryAxis, leAxis);
//     }

//     public void fillCycloplegicValues(String reSph, String reCyl, String reAxis,
//                                       String leSph, String leCyl, String leAxis) {
//         selectSelect2Dropdown(reCycloSphVal, reSph);
//         selectSelect2Dropdown(reCycloCylVal, reCyl);
//         selectSelect2Dropdown(reCycloAxisVal, reAxis);

//         selectSelect2Dropdown(leCycloSphVal, leSph);
//         selectSelect2Dropdown(leCycloCylVal, leCyl);
//         selectSelect2Dropdown(leCycloAxisVal, leAxis);
//     }

//     public void fillAdditionalAdd(String reAdd, String leAdd) {
//         selectSelect2Dropdown(reAddExtra, reAdd);
//         selectSelect2Dropdown(leAddExtra, leAdd);
//     }

//     public void fillNearVisionExtra(String reVal, String leVal) {
//         if (reVal != null && !reVal.isEmpty()) {
//             selectSelect2Dropdown(reNearVisionExtra, reVal);
//             System.out.println("RE Near Vision selected: " + reVal);
//         }

//         if (leVal != null && !leVal.isEmpty()) {
//             selectSelect2Dropdown(leNearVisionExtra, leVal);
//             System.out.println("LE Near Vision selected: " + leVal);
//         }
//     }

//     public void selectIOPValue(String reValue, String leValue) {
//         if (reValue != null && !reValue.isEmpty()) {
//             selectSelect2Dropdown(reIOPValueDropdown, reValue);
//             System.out.println("RE IOP Value selected: " + reValue);
//         }

//         if (leValue != null && !leValue.isEmpty()) {
//             selectSelect2Dropdown(leIOPValueDropdown, leValue);
//             System.out.println("LE IOP Value selected: " + leValue);
//         }
//     }

//     // ===== FULL VISUAL ACUITY FLOW =====
//     public void fillVisualAcuityForm(String spectacles, String years, String months, String purpose,
//                                      String reUnaided, String reSpecs, String rePH, String reNear,
//                                      String leUnaided, String leSpecs, String lePH, String leNear,
//                                      String colorVision, String stereopsis, String contrast) {
//         openVisualAcuityTab();

//         selectSpectacles(spectacles);
//         enterSpectacleAge(years, months);
//         enterPurpose(purpose);

//         fillRightEye(reUnaided, reSpecs, rePH, reNear);
//         fillLeftEye(leUnaided, leSpecs, lePH, leNear);

//         clickSave();
//         handleSuccessPopup();
//         waitForVisualAcuitySaveComplete();

//         selectColorVision(colorVision);
//         selectStereopsis(stereopsis);
//         enterContrast(contrast);

//         clickOtherSave();
//         handleSuccessPopup();

//         waitForRefractionSection();
//     }

//     private void waitForVisualAcuitySaveComplete() {
//         wait.until(d -> ((JavascriptExecutor) d)
//                 .executeScript("return document.readyState").equals("complete"));

//         waitForAjaxToFinish();

//         try {
//             Thread.sleep(700);
//         } catch (InterruptedException e) {
//             Thread.currentThread().interrupt();
//         }

//         System.out.println("Visual Acuity saved and stable");
//     }

//     private void waitForRefractionSection() {
//         wait.until(ExpectedConditions.presenceOfElementLocated(rePGSph));
//         wait.until(ExpectedConditions.visibilityOfElementLocated(rePGSph));
//         wait.until(ExpectedConditions.elementToBeClickable(rePGSph));

//         waitForAjaxToFinish();

//         try {
//             Thread.sleep(500);
//         } catch (InterruptedException e) {
//             Thread.currentThread().interrupt();
//         }

//         System.out.println("Refraction section ready");
//     }

//     private void enterCycloplegicRemark(String value) {
//         By locator = By.id("CE_txtCycloplegic_RE_Remark");

//         WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

//         JavascriptExecutor js = (JavascriptExecutor) driver;
//         js.executeScript(
//                 "arguments[0].removeAttribute('disabled');" +
//                 "arguments[0].removeAttribute('readonly');" +
//                 "arguments[0].value = arguments[1];" +
//                 "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
//                 "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
//                 element, value
//         );

//         System.out.println("Cycloplegic remark entered forcefully");
//     }

//     public void fillRefractionForm(String reSph, String reCyl, String reAxis, String reAdd,
//                                    String reFinalSph, String reFinalCyl,
//                                    String reFinalAxis, String reFinalAdd,
//                                    String leSph, String leCyl, String leAxis, String leAdd,
//                                    String leFinalSph, String leFinalCyl,
//                                    String leFinalAxis, String leFinalAdd) {
//         fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
//                 reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);

//         fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
//                 leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);

//         clickRefractionSave();
//         handleSuccessPopup();
//     }

//     public void fillAdditionalRefractionDetails(String reDry, String leDry,
//                                                 String reCyclo, String leCyclo,
//                                                 String reAddRem, String leAddRem,
//                                                 String reNpc, String leNpc,
//                                                 String reIOPVal, String leIOPVal,
//                                                 String time) {
//         fillDryRetinoscopy(reDry, leDry);
//         fillCycloplegic(reCyclo, leCyclo);
//         fillAddRemark(reAddRem, leAddRem);
//         fillNearVisionExtra(reNpc, leNpc);
//         fillIOP(reIOPVal, leIOPVal, time);
//         enterCycloplegicRemark("Test");
//     }

//     public void fillCompleteRefraction(
//             String reSph, String reCyl, String reAxis, String reAdd,
//             String leSph, String leCyl, String leAxis, String leAdd,
//             String reDrySphVal, String reDryCylVal, String reDryAxisVal,
//             String leDrySphVal, String leDryCylVal, String leDryAxisVal,
//             String reCycloSph, String reCycloCyl, String reCycloAxis,
//             String leCycloSph, String leCycloCyl, String leCycloAxis,
//             String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
//             String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,
//             String reAddExtraVal, String leAddExtraVal
//     ) {
//         fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
//                 reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);

//         fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
//                 leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);

//         fillDryRetinoscopyValues(reDrySphVal, reDryCylVal, reDryAxisVal,
//                 leDrySphVal, leDryCylVal, leDryAxisVal);

//         fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis,
//                 leCycloSph, leCycloCyl, leCycloAxis);

//         fillAdditionalAdd(reAddExtraVal, leAddExtraVal);

//         clickRefractionSave();
//         handleSuccessPopup();
//     }

//     public void fillFullRefractionFlow(
//             String reSph, String reCyl, String reAxis, String reAdd,
//             String leSph, String leCyl, String leAxis, String leAdd,
//             String reDrySph, String reDryCyl, String reDryAxis,
//             String leDrySph, String leDryCyl, String leDryAxis,
//             String reCycloSph, String reCycloCyl, String reCycloAxis,
//             String leCycloSph, String leCycloCyl, String leCycloAxis,
//             String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
//             String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,
//             String reAddExtraVal, String leAddExtraVal,
//             String reDryRemarkVal, String leDryRemarkVal,
//             String reCycloRemarkVal, String leCycloRemarkVal,
//             String reAddRemarkVal, String leAddRemarkVal,
//             String reNpc, String leNpc,
//             String reIOP, String leIOP, String iopTimeVal
//     ) {
//         waitForRefractionSection();

//         fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
//                 reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);

//         fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
//                 leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);

//         fillDryRetinoscopyValues(reDrySph, reDryCyl, reDryAxis,
//                 leDrySph, leDryCyl, leDryAxis);

//         fillDryRetinoscopy(reDryRemarkVal, leDryRemarkVal);

//         fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis,
//                 leCycloSph, leCycloCyl, leCycloAxis);

//         fillCycloplegic(reCycloRemarkVal, leCycloRemarkVal);

//         fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
//         fillAddRemark(reAddRemarkVal, leAddRemarkVal);

//         fillNearVisionExtra(reNpc, leNpc);
//         fillIOP(reIOP, leIOP, iopTimeVal);

//         waitForAjaxToFinish();

//         clickRefractionSave();
//         handleSuccessPopup();

//         System.out.println("Full Refraction Flow Completed");
//     }

//     private void waitForAjaxToFinish() {
//         wait.until(d -> (Boolean) ((JavascriptExecutor) d).executeScript(
//                 "return document.readyState === 'complete' && " +
//                 "(!window.jQuery || jQuery.active === 0);"
//         ));
//     }
// }


