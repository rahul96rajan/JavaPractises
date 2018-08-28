package practisePckg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HigherEd {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("http://ohcinmercuryqaap01.cmss.cengage.info:7003/Per_Periodical/");
		driver.findElement(By.name("userName")).sendKeys("ADHYANI");
		driver.findElement(By.name("password")).sendKeys("ADHYANI");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		driver.findElement(By.xpath("//td[contains(text(),'Higher')]")).click();
		String URL = driver.getCurrentUrl();
		String isUporOver = driver.findElement(By.cssSelector("img[id='GetWork']")).getAttribute("src");
		if(URL.contains("HIGHERED"))
			System.out.println("### URL is OK ### -> " + URL);
		if(isUporOver.contains("Over")) 
			System.out.println("### Get Work is OK ### -> " + isUporOver);
		driver.quit();
		}
}
