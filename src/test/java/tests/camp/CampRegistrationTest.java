package tests.camp;

import java.util.List;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.camp.CampRegistrationPage;
import utils.CampRegistrationData;
import utils.ExcelUtils;
import utils.TestDataGenerator;

public class CampRegistrationTest extends BaseTest {

	@Test
	public void verifyCampRegistration() {

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("ravi", "sceh@123");

		CampRegistrationPage campPage = new CampRegistrationPage(driver);
		campPage.goToNewRegistration();

		List<CampRegistrationData> patients = ExcelUtils
				.getCampRegistrationData("src/test/resources/testdata/CampPatientRegistrationReport.xlsx");

		System.out.println("Total patients from Excel: " + patients.size());

		int registrationLimit = 1; // yahan 1, 5, 10, 20 jo chahiye set karo
		int count = 0;

		for (CampRegistrationData patient : patients) {

			if (count >= registrationLimit) {
				break;
			}

			count++;

			patient.setNextOfKin(TestDataGenerator.getNextOfKin());
			patient.setDrivingExp(TestDataGenerator.getDrivingExperience());

			try {
				System.out.println("========== PATIENT DATA ==========");
				System.out.println("Registration No : " + count);
				System.out.println("Gender : " + patient.getGender());
				System.out.println("Age Year : " + patient.getAgeYear());
				System.out.println("Age Month : " + patient.getAgeMonth());
				System.out.println("Next Of Kin : " + patient.getNextOfKin());
				System.out.println("District From Excel = [" + patient.getDistrict() + "]");
				System.out.println("Qualification : " + patient.getQualification());
				System.out.println("Occupation : " + patient.getOccupation());
				System.out.println("Identity Type : " + patient.getIdentityType());
				System.out.println("Previous Eye : " + patient.getPreviousEyeCheckup());
				System.out.println("Eye Center : " + patient.getEyeExaminedCenter());
				System.out.println("Previous Ear : " + patient.getPreviousEarCheckup());
				System.out.println("Ear Center : " + patient.getEarExaminedCenter());
				System.out.println("==================================");

				campPage.fillCampRegistrationForm(patient);
				campPage.submitForm();

				System.out.println("Registration Successful: " + count);

			} catch (Exception e) {
				System.out.println("Registration Failed: " + count);
				e.printStackTrace();
			}

			campPage.waitForPageReady();

			if (count < registrationLimit) {
				campPage.goToNewRegistration();
			}
		}
	}
}
