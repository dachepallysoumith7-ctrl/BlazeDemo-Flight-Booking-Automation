package project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class FlightBookingAutomation {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 1: Navigate to application
            driver.get("https://blazedemo.com/");

            // Step 2: Select departure city
            Select departureCity = new Select(driver.findElement(By.name("fromPort")));
            departureCity.selectByVisibleText("Boston");
            Thread.sleep(1000);

            // Step 3: Select destination city
            Select destinationCity = new Select(driver.findElement(By.name("toPort")));
            destinationCity.selectByVisibleText("London");
            Thread.sleep(1000);

            // Step 4: Click Find Flights
            driver.findElement(By.cssSelector("input[type='submit']")).click();
            Thread.sleep(1000);

            // Step 5: Verify available flights displayed
            List<WebElement> flights =
                    driver.findElements(By.xpath("//table/tbody/tr"));

            if (flights.size() > 0) {
                System.out.println("Available flights are displayed.");
            } else {
                throw new RuntimeException("No flights found.");
            }

            // Step 6: Select first flight
            driver.findElement(
                    By.xpath("//table/tbody/tr[1]/td[1]/input"))
                    .click();
            Thread.sleep(1000);

            // Step 7: Verify Purchase Flight page
            String pageTitle = driver.findElement(
                    By.tagName("h2")).getText();
            Thread.sleep(1000);

            if (pageTitle.contains("Your flight from")) {
                System.out.println("Purchase Flight page displayed.");
            } else {
                throw new RuntimeException("Purchase Flight page not displayed.");
            }

            // Verify total cost exists
            String totalCost = driver.findElement(
                    By.xpath("//p[contains(text(),'Price')]"))
                    .getText();
            Thread.sleep(1000);

            System.out.println("Flight Details Verified: " + totalCost);

            // Step 8: Enter personal details
            driver.findElement(By.id("inputName"))
                    .sendKeys("Soumith Dachepally");
            Thread.sleep(1000);

            driver.findElement(By.id("address"))
                    .sendKeys("7-23,Atal road Banglore");
            Thread.sleep(1000);

            driver.findElement(By.id("city"))
                    .sendKeys("Banglore");
            Thread.sleep(1000);

            driver.findElement(By.id("state"))
                    .sendKeys("KA");
            Thread.sleep(1000);

            driver.findElement(By.id("zipCode"))
                    .sendKeys("602300");
            Thread.sleep(1000);

            // Step 9: Enter payment details
            Select cardType =
                    new Select(driver.findElement(By.id("cardType")));
            cardType.selectByVisibleText("Visa");
            Thread.sleep(1000);

            driver.findElement(By.id("creditCardNumber"))
                    .sendKeys("4111111111111111");
            Thread.sleep(1000);

            driver.findElement(By.id("creditCardMonth"))
                    .clear();
            driver.findElement(By.id("creditCardMonth"))
                    .sendKeys("12");

            driver.findElement(By.id("creditCardYear"))
                    .clear();
            driver.findElement(By.id("creditCardYear"))
                    .sendKeys("2028");

            driver.findElement(By.id("nameOnCard"))
                    .sendKeys("Soumith Dachepally");

            // Optional checkbox
            WebElement rememberMe =
                    driver.findElement(By.id("rememberMe"));

            if (!rememberMe.isSelected()) {
                rememberMe.click();
            }

            // Step 10: Purchase Flight
            driver.findElement(
                    By.cssSelector("input[type='submit']"))
                    .click();
            Thread.sleep(1000);

            // Step 11: Verify booking success
            String confirmation =
                    driver.findElement(By.tagName("h1")).getText();

            if (confirmation.contains("Thank you for your purchase today!")) {
                System.out.println("Flight booking completed successfully.");
                System.out.println("Confirmation Message: " + confirmation);
            } else {
                throw new RuntimeException("Booking confirmation not found.");
            }

        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
        } finally {
        	driver.close();
            
        }
    }
}
