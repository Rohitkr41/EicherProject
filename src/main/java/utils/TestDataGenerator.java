package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

public class TestDataGenerator {
	static Faker faker = new Faker();

	public static String getFirstName() {
		return faker.name().firstName();
	}

	public static String getLastName() {
		return faker.name().lastName();
	}
	
	public static String getNextOfKin() {
	    return faker.name().lastName() + " Family";
	}

	public static String getMobileNumber() {
		return "9" + faker.number().digits(9);
	}

	public static String getDrivingLicense() {
		return "DL" + faker.number().digits(10);
	}
	
	public static String getDrivingExperience() {
	    return String.valueOf(ThreadLocalRandom.current().nextInt(1, 15));
	}

	public static String getAddress() {
		return faker.address().streetAddress();
	}

	public static String getPastDate() {
		LocalDate today = LocalDate.now();

		long randomDaysBack = ThreadLocalRandom.current().nextLong(1, 3651);

		LocalDate pastDate = today.minusDays(randomDaysBack);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return pastDate.format(formatter);
	}
}