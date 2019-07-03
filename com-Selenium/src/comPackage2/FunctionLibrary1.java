package comPackage2;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class FunctionLibrary1 extends CommonFunctions{
	//static FunctionLibrary2 fn2=new FunctionLibrary2();
	//use fn2 to access all objects and functions in FunctionLibrary2.class file
	
	//This Function is used to launch IE / Chrome
	public static void LaunchBrowser() throws IOException, InterruptedException 
	{
		System.out.println("Launch browser");
		//Code to get the browser name from excel data sheet
		String TestBrowser=DataTableValue("Browser").toString().trim().toUpperCase();
		System.out.println("Browser :"+TestBrowser);
		//to launch the IE or chrome
		if(TestBrowser.equals("IE")){
			System.setProperty("webdriver.ie.driver","Drivers/IEDriverServer64.exe");
			System.out.println("Driver initialized");
			driver = new InternetExplorerDriver();
			System.out.println("Driver initialized");
			//datatabel is a custom function and it will get the url from the datasheet
			driver.get(DataTableValue("URL"));
			if(Exist("xpath","//a[@id='nav-cart']")){
			HtmlReport("IE Browser launched and navigated to "+DataTableValue("URL"),"PASS");
			}
		}
		else if(TestBrowser.equals("FIREFOX"))
		{
			System.setProperty("webdriver.firefox.marionette","Drivers/geckodriver.exe");
			driver=new FirefoxDriver();
			driver.get(DataTableValue("URL"));
			if(Exist("xpath","//a[@id='nav-cart']")){
			HtmlReport("Firefox Browser launched and navigated to "+DataTableValue("URL"),"PASS");
			}
		}
		
		else
		{
			System.setProperty("webdriver.chrome.driver","Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(DataTableValue("URL"));
			Thread.sleep(5000);
			HtmlReport("Chrome Browser launched and navigated to "+DataTableValue("URL"),"PASS");
		}
		
	}
	
	//function to validate the empty cart message
	
		public static void emptyCartMessage() throws IOException{
	
			if(Exist("xpath","//a[@id='nav-cart']"))
			{
				
				boolean blnValue=ClickOnElement("xpath","//a[@id='nav-cart']");
				if(blnValue)
				{
					HtmlReport("Clicked on cart ","PASS");

					//check the visibility of the element
					if(Exist("xpath","//h1[1]"))
					{

						try{
								String strHeader=driver.findElement(By.xpath("//h1[1]")).getText();
								if(strHeader.contains("Your Shopping Cart is empty"))
								{
									HtmlReport("Cart is empty message displayed as : "+strHeader,"PASS");
								}
								else
								{
									HtmlReport("Cart is empty message displayed as : "+strHeader,"FAIL");
								}
							}
						catch(Exception ex)
							{
								System.out.println("inside cart failed");
							}
					
					}
					else
					{
						System.out.println("inside cart failed");
						HtmlReport("Cart is empty header not dispalyed ","FAIL");
					}
					
					
				}
				else
				{
					HtmlReport("Clicked on cart Failed ","FAIL");
				}
			}
			else
			{
				HtmlReport("Cart button is not exist in the web page","FAIL");
			}
			
	}



// this function is to determine the mouse hover in language
		public static void MouseHoverOnLanguages() throws InterruptedException
		{
			//example 1
			/*Actions action = new Actions(driver);
			WebElement we = driver.findElement(By.xpath("//span[@class='icp-nav-link-inner']"));
			action.moveToElement(we).moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Español - ES')]")));
			*/
			//example 2
			Thread.sleep(500);
			Actions builder = new Actions(driver);
			WebElement we = driver.findElement(By.xpath("//a[@id='nav-link-accountList']"));
			builder.moveToElement(we).perform();
			if(Exist("xpath","//span[contains(text(),'Create a List')]")) {
			driver.findElement(By.xpath("//span[contains(text(),'Create a List')]")).click();
			}
			else
			{
				System.out.println("not exist the list");
			}
			
		}


	
	}
