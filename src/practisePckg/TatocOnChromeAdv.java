package practisePckg;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TatocOnChromeAdv {
	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text()='Advanced Course']")).click();

		// hover/menu
		WebElement Menu2 = driver.findElement(By.cssSelector("div[class='menutop m2']"));
		Actions act = new Actions(driver);
		act.moveToElement(Menu2).build().perform();
		driver.findElement(By.cssSelector("span[onclick='gonext();']")).click();

		// query/gate
		try {
			String symbol = driver.findElement(By.cssSelector("#symboldisplay")).getText();
			String host = "10.0.1.86";
			String usrName = "tatocuser";
			String pwd = "tatoc01";
			String port = "3306";
			String databaseName = "tatoc"; //
			Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName,
					usrName, pwd);
			Statement stmt = con.createStatement();
			ResultSet rsI = stmt.executeQuery("SELECT * from identity where symbol='" + symbol + "'"); // for getting
																										// ID.
			rsI.next();
			System.out.println(rsI.getString("id"));
			ResultSet rsC = stmt.executeQuery("SELECT * from credentials where id=" + rsI.getString("id"));
			rsC.next();
			System.out.println(rsC.getString("name"));
			System.out.println(rsC.getString("passkey"));

			driver.findElement(By.id("name")).sendKeys(rsC.getString("name"));
			driver.findElement(By.id("passkey")).sendKeys(rsC.getString("passkey"));
			driver.findElement(By.id("submit")).click();
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		// video/player --> got to be skipped indeed
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.played = true;");
		driver.findElement(By.linkText("Proceed")).click();

		// rest

		String session = driver.findElement(By.cssSelector("#session_id")).getText();
		String session_ID = session.split(": ")[1];
		System.out.println("Session id = " + session_ID);

		// Rest Service to generate token
		URL geturl = new URL("http://10.0.1.86/tatoc/advanced/rest/service/token/" + session_ID);
		HttpURLConnection getconn = (HttpURLConnection) geturl.openConnection();
		getconn.setRequestMethod("GET");
		getconn.setRequestProperty("Accept", "application/json");

		if (getconn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + getconn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((getconn.getInputStream())));
		System.out.println(br);
		String output;
		String restful = new String();
		while ((output = br.readLine()) != null) {
			restful = restful.concat(output);
		}
		br.close();
		System.out.println(restful);
		String response[] = restful.split(":\"");
		String token[] = response[1].split("\"");
		String jsonToken = token[0];
		System.out.println("jsontoken: " + jsonToken);

		// POST BODY: id=[Session ID], signature=[Token], allow_access=1
		// Rest Service to register for access
		try {

			URL posturl = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
			HttpURLConnection postconn = (HttpURLConnection) posturl.openConnection();
			postconn.setDoOutput(true);
			postconn.setRequestMethod("POST");
			postconn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String input = "id=" + session_ID + "& signature=" + jsonToken + "&allow_access=1";
			System.out.println("Post parameters : " + input);

			DataOutputStream wr = new DataOutputStream(postconn.getOutputStream());
			wr.writeBytes(input);
			wr.flush();
			wr.close();

			int responseCode = postconn.getResponseCode();
			System.out.println("Response Code : " + responseCode);
			System.out.println(postconn.getResponseMessage());

			postconn.disconnect();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Proceed']")));
			driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// File Handle
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'download')]")));
		driver.findElement(By.xpath("//a[contains(@href,'download')]")).click();
		Thread.sleep(5000);
		File file = new File("C:\\Users\\rahulrajan\\Downloads\\file_handle_test.dat");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("signature")).sendKeys(prop.getProperty("Signature"));
		driver.findElement(By.className("submit")).click();
		System.out.println("property: " + prop.getProperty("Signature"));



	}

}
