package hckrrnk;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class HttpStsRspns {
	
	WebDriver driver = new ChromeDriver();
	
	public List<WebElement> getAllElements() {
		driver.get("http://live.guru99.com/index.php/");
		List<WebElement> links = driver.findElements(By.xpath("//a"));
		return links;
	}
	
	public ArrayList<String> getActiveLinks(){
		List<WebElement> links = getAllElements();
		ArrayList<String> urls = new ArrayList<String>();
		for(WebElement elem : links) {
//			if(!elem.getAttribute("href").startsWith("#")) {
				urls.add(elem.getAttribute("href"));
//			}
		}
		return urls;
	}
	
	public void testGetResp(String link) throws IOException {
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int code = connection.getResponseCode();
		System.out.println(link + " -->  " + code);
	}

	public void run() throws IOException {
		ArrayList<String> urls = getActiveLinks();
		for(String url : urls) {
			testGetResp(url);
		}
	}

}
