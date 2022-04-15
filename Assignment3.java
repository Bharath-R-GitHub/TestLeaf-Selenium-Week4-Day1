package week4.day1.assignments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment3 {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Launch URL
		driver.get("https://chercher.tech/practice/frames-example-selenium-webdriver");
		driver.manage().window().maximize();

		// 2. Handle element in frame-1
		driver.switchTo().frame("frame1");
		driver.findElement(By.xpath("//body/b[@id='topic']/following-sibling::input")).sendKeys("Not a Friendly Topic");

		// 3. Handle element in frame-3
		driver.switchTo().frame("frame3");
		driver.findElement(By.xpath("//body/b[text()='Inner Frame Check box :']/following-sibling::input")).click();

		driver.switchTo().defaultContent();

		// 4. Handle element in frame-2
		driver.switchTo().frame("frame2");
		WebElement dropDown = driver.findElement(By.xpath("//body/select[@id='animals']"));
		Select dd = new Select(dropDown);
		dd.selectByVisibleText("Baby Cat");
	}
}
