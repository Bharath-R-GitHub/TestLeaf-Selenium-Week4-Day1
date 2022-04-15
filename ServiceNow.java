package week4.day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServiceNow {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Load ServiceNow application URL
		driver.get("https://dev121343.service-now.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		driver.switchTo().frame(0);

		// 2. Enter username
		driver.findElement(By.id("user_name")).sendKeys("admin");

		// 3. Enter password
		driver.findElement(By.id("user_password")).sendKeys("India@123");

		// 4. Click Login
		driver.findElement(By.id("sysverb_login")).click();
		Thread.sleep(2000);

		driver.switchTo().defaultContent();

		// 5. Search "incident" Filter Navigator
		driver.findElement(By.xpath("(//div[@class='sn-widget-list-content']/div[text()='Incidents'])[1]")).click();

		WebElement frameHandler = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(frameHandler);

		// 6. Click “All”
		driver.findElement(By.xpath("//span[@id='incident_breadcrumb']/a/b[text()='All']")).click();
		driver.switchTo().defaultContent();

		driver.switchTo().frame(frameHandler);

		// 7. Click New button
		driver.findElement(By.xpath("(//button[@type='submit'])[1]")).click();
		driver.switchTo().defaultContent();

		driver.switchTo().frame(frameHandler);

		// 8. Select a value for Caller and Enter value for short_description
		driver.findElement(By.id("lookup.incident.caller_id")).click();

		Set<String> windowHandles = driver.getWindowHandles();
		List<String> window = new ArrayList<String>(windowHandles);
		driver.switchTo().window(window.get(1));

		driver.findElement(By.xpath("(//tbody[@class='list2_body']//td[3]/a)[1]")).click();
		driver.switchTo().window(window.get(0));
		driver.switchTo().frame(frameHandler);

		driver.findElement(By.xpath("//input[@id='incident.short_description']"))
				.sendKeys("Creating Ticket for Testing purpose");

		// 9. Read the incident number and save it a variable
		String incidentNumber = driver.findElement(By.xpath("//input[@id='incident.number']")).getAttribute("value");
		System.out.println("The Incident Number is: " + incidentNumber);

		// 10. Click on Submit button
		driver.findElement(By.xpath("//span[@class='navbar_ui_actions']/button[text()='Submit']")).click();

		// 11. Search the same incident number in the next search screen
		driver.findElement(By.xpath("//div[@class='input-group']/input[@placeholder='Search']"))
				.sendKeys(incidentNumber);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='input-group']/input[@placeholder='Search']")).sendKeys(Keys.ENTER);

		// 12. Verify the incident is created successful.
		String incidentFound = driver
				.findElement(By.xpath("//tbody[@class='list2_body']/tr[1]/td/a[@class='linked formlink']")).getText();
		if (incidentFound.equals(incidentNumber)) {
			System.out.println("The Incident " + incidentNumber + " is Created Successfully");
		} else {
			System.out.println("The Incident " + incidentNumber + " is not Created");

		}

	}

}
