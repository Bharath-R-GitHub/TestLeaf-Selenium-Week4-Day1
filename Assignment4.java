package week4.day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment4 {
	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Launch URL
		driver.get("http://leafground.com/pages/frame.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 2. Take the the screenshot of the click me button of first frame
		driver.switchTo().frame(0);
		Thread.sleep(2000);
		WebElement clickMe = driver.findElement(By.xpath("//button[@id='Click']"));
		clickMe.click();
		Thread.sleep(2000);
		File source = clickMe.getScreenshotAs(OutputType.FILE);
		File destination = new File("./screenshots/click_me.png");
		FileUtils.copyFile(source, destination);

		driver.switchTo().defaultContent();

		// 3. Find the number of frames
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		System.out.println("The number of Frames available in the Current Page is " + frames.size());
	}
}
