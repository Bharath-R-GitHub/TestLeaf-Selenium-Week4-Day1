package week4.day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment5 {
	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 2. Mouseover on Brands and Search L'Oreal Paris
		WebElement brands = driver.findElement(By.xpath("//div[@id='headerMenu']/div/ul[2]/li/a"));
		WebElement brandSearchBox = driver.findElement(By.id("brandSearchBox"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brands).pause(1000).moveToElement(brandSearchBox).click().sendKeys("L'Oreal Paris")
				.perform();

		// 3. Click L'Oreal Paris
		driver.findElement(By.xpath("//div[@id='list_L']/following-sibling::div/a")).click();

		// 4. Check the title contains L'Oreal Paris(Hint-GetTitle)
		String title = driver.getTitle();
		System.out.println(title);
		if (title.contains("L'Oreal Paris")) {
			System.out.println("The Title of the Current Page contains L'Oreal Paris." + " Hence VALIDATION SUCCESS");
		} else {
			System.out.println(
					"The Title of the Current Page does not contain L'Oreal Paris." + " Hence VALIDATION FAILED");
		}

		// 5. Click sort By and select customer top rated
		driver.findElement(By.xpath("//div[@id='filter-sort']//div/button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div[contains(@class,'control-box')])[4]")).click();

		// 6. Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.xpath("//div[@id='first-filter']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='first-filter']/ul/ul/li[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li//span[text()='Hair Care']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//label[@for='checkbox_Shampoo_316']/div[2]")).click();
		Thread.sleep(2000);

		// 7. Click->Concern->Color Protection
		driver.findElement(By.xpath("//div[@id='first-filter']/ul/ul/li[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li//span[text()='Shop By Concern']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//label[@for='checkbox_Color Protection_2045']")).click();
		Thread.sleep(2000);

		// 8. check whether the Filter is applied with Shampoo
		List<WebElement> filter = driver.findElements(By.xpath("//span[@class='filter-value']"));
		List<String> filterResults = new ArrayList<String>();

		for (int i = 0; i < filter.size(); i++) {
			String text = filter.get(i).getText();
			filterResults.add(text);
		}

		boolean status = false;
		for (int i = 0; i < filterResults.size(); i++) {
			if (filterResults.get(i).equalsIgnoreCase("shampoo")) {
				status = true;
				break;
			} else {
				status = false;
			}
		}

		if (status) {
			System.out.println("The Filter is Applied with SHAMPOO. Hence VALIDATION SUCCESS");
		} else {
			System.out.println("The Filter is not Applied with SHAMPOO. Hence VALIDATION FAILED");
		}

		// 9. Click on L'Oreal Paris Colour Protect Shampoo
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//img[contains(@alt,'Oreal Paris Colour Protect Shampoo')]")).click();

		// 10. GO to the new window and select size as 175ml
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windows = new LinkedList<String>(windowHandles);
		driver.switchTo().window(windows.get(1));
		Thread.sleep(2000);

		WebElement dropDown = driver.findElement(By.xpath("//select[@title='SIZE']"));
		Select option = new Select(dropDown);
		option.selectByValue("0");

		// 11. Print the MRP of the product
		String productName = driver.findElement(By.xpath("//h1")).getText();
		String mrp = driver.findElement(By.xpath("//span[text()='MRP:']/following-sibling::span")).getText();
		System.out.println("The MRP of the Product " + productName + " is RS:" + mrp.substring(1));

		// 12.Click on ADD to BAG
		driver.findElement(By.xpath("//span[text()='ADD TO BAG']/parent::button")).click();

		// 13. Go to Shopping Bag
		driver.findElement(By.xpath("(//button/*)[2]")).click();
		Thread.sleep(2000);

		// 14. Print the Grand Total amount
		WebElement frame = driver.findElement(By.xpath("//div[@id='portal-root']//iframe"));
		driver.switchTo().frame(frame);

		String totalAmount = driver.findElement(By.xpath("//span[text()='Grand Total']/following-sibling::div"))
				.getText();
		System.out.println("---AMOUNT AT STEP 14---");
		System.out
				.println("The Grand Total Amount of the Product " + productName + " is RS:" + totalAmount.substring(1));

		totalAmount = totalAmount.substring(1);

		// 15. Click Proceed
		driver.findElement(By.xpath("//span[text()='PROCEED']/ancestor::button")).click();

		// 16. Click on Continue as Guest
		driver.findElement(By.xpath("//button[contains(text(),'GUEST')]")).click();
		Thread.sleep(2000);

		// 17. Check if this grand total is the same in step 14
		String grandTotal = driver
				.findElement(
						By.xpath("//div[contains(@class,'payment-details-container')]/div[2]/div[@class='value']/span"))
				.getText();
		System.out.println("---AMOUNT IN THE CHECKOUT PAGE IS RS:" + grandTotal + "---");
		if (totalAmount.equals(grandTotal)) {
			System.out.println("The Grand Total is EQUAL to Total Amount found Earlier. Hence VALIDATION SUCCESS");
		} else {
			System.out.println("The Grand Total is NOT EQUAL to Total Amount found Earlier. Hence VALIDATION FAILED");
		}

		// 18. Close all windows
		driver.quit();
	}
}
