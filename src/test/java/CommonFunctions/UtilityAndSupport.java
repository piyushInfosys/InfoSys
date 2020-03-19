package CommonFunctions;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class UtilityAndSupport {
	public static Properties OR;
	public final static Logger logger = Logger.getLogger("rootLogger");

	public void loadpropertiesFIle() {
		// TODO Auto-generated method stub
		try { 
			logger.info("Configuring and loading the properties files");
			OR = new Properties(); 
			FileInputStream fis = new FileInputStream("C:\\Users\\Admin\\workspace\\InfoSys\\src\\test\\java\\OR.properties");
			OR.load(fis);
			logger.info("Object Repositery properties file is assigned with FIS and loaded.");
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(""+ex.getMessage());
		}
		
		
	}
	
	
	
	
	public WebDriver setRequiredCapabilities() throws MalformedURLException {
		WebDriver dr =null;
		System.out.println("This is function to set the desired capabilities...");
		logger.info("Executing the setRequiredCapabilities() method to set the desired capabilities and pass to the appium driver.");
		try {
			//Android	FCAZGU0373269S7		5.0.2	ASUS_ZENFONE
			
		DesiredCapabilities caps = new DesiredCapabilities();
		
		caps.setCapability("platformName", "Android");	
		caps.setCapability("udid", "FCAZGU0373269S7");			
		caps.setCapability("platformVersion", "5.0.2");
		caps.setCapability("deviceName", "ASUS_ZENFONE");
		caps.setCapability("autoGrantPermissions", "true");
		caps.setCapability("WRITE_SECURE_SETTINGS", "true");
		caps.setCapability("appPackage", "com.amazon.mShop.android.shopping");
		caps.setCapability("appActivity", "com.amazon.mShop.splashscreen.StartupActivity");
		
		System.out.println("Capabilities are set......");
		
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		
		dr = new AppiumDriver<MobileElement>(url, caps);
		System.out.println("WebDriver refrence is assigned with appium driver object.");
		
		dr.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
		System.out.println("Amazon application is started.....");
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception while executing setRequiredCapabilities() method is ::"+ex.getMessage());
			logger.info("Cause of the exception is :: "+ex.getCause());
		}
		return dr;
		
	}

	public void loginToApp(WebDriver dr) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("This is function to login to app.....");
		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		loadpropertiesFIle();
		
		dr.findElement(By.id(OR.getProperty("signIn_btn"))).click();
		if ((dr.findElement(By.xpath(OR.getProperty("login_chkBx"))).isSelected())) {
			dr.findElement(By.xpath(OR.getProperty("login_chkBx"))).click();
			System.out.println("Already customer checkbox is selected....");
		}
		
		dr.findElement(By.xpath(OR.getProperty("email_txtBx"))).clear();
		dr.findElement(By.xpath(OR.getProperty("email_txtBx"))).sendKeys("1111111111");
		
		dr.findElement(By.xpath(OR.getProperty("amazon_txt"))).click();
		Thread.sleep(3000);
		dr.findElement(By.xpath(OR.getProperty("continue_button"))).click();
		
		Thread.sleep(3000);
		dr.findElement(By.xpath(OR.getProperty("password_txtBx"))).sendKeys("********");
		
		dr.findElement(By.xpath(OR.getProperty("login_Btn"))).click();
		Thread.sleep(4000);
		System.out.println("Login to app is successful.........");
	}

	public void searchAndSelectTV(WebDriver dr) {
		// TODO Auto-generated method stub
		System.out.println("This function is for searching the TV....");
		try { 
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("Hello.....  This is Amazon Sale Android appium test with selenium JAVA");
			dr.findElement(By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']")).click();
			Thread.sleep(2000);
			dr.findElement(By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']")).sendKeys("65-inch TV");
			Thread.sleep(2000);
			
			
			dr.findElement(By.xpath("(//android.widget.TextView[@text='65-inch tv'])[1]")).click();
			Thread.sleep(2000);
			
			dr.findElement(By.xpath("//android.widget.TextView[contains(@text, 'QA65Q90RAKXX')]")).click();
			Thread.sleep(2000);
		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		System.out.println("TV search is completed.....");
		
	}

	public void getTVdetails(WebDriver dr) {
		// TODO Auto-generated method stub
		System.out.println("This function is to get the dtails of TV");
		try {
		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String name = dr.findElement(By.xpath("//android.view.View[contains(@content-desc, 'QA65Q90RAKXXL')]")).getAttribute("content-desc");
		System.out.println("Name of the 65 inch TV is ::  "+name);
		String price = dr.findElement(By.xpath("//android.widget.EditText[@content-desc='rupees 3,30,501']")).getAttribute("content-desc");
		System.out.println("Price of the 65 inch TV is ::  "+price);
		
		goToCheckoutScreen(dr);
		
		String result = validateDetails(dr, name, price);
		if (result.equals("PASS"))
			System.out.println("Test case is passed....");
		else 
			System.out.println("Test case is failed....");
		/*To check TV is available or not
		List<MobileElement> availablity;
		availablity = dr.findElements(By.xpath("//android.view.View[@content-desc='In stock.']"));
		if (availablity.size() == 1)
			System.out.println("65 inch TV is in stock ie. available....");
		else 
			System.out.println("65 inch TV not in stock ie. not available....");*/
		
		System.out.println("TV details are captured.....");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private String validateDetails(WebDriver dr, String name, String price) {
		// TODO Auto-generated method stub
		System.out.println("This function is to validate the details of the selected TV on checkout page....");
		try {
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String chk_name = dr.findElement(By.xpath("//android.view.View[contains(@content-desc, 'QA65Q90RAKXXL')]")).getAttribute("content-desc");
			System.out.println("Name of the 65 inch TV on CheckOut screen is ::  "+chk_name);
			/*String chk_price = dr.findElement(By.xpath("//android.widget.EditText[@content-desc='rupees 3,30,501']")).getAttribute("content-desc");
			System.out.println("Price of the 65 inch TV is ::  "+chk_price);*/
			Assert.assertEquals(name, chk_name);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
		return "PASS";
	}

	private void goToCheckoutScreen(WebDriver dr) {
		// TODO Auto-generated method stub
		System.out.println("This is function to go on checkout screen.....");
		try {
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			dr.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"bylineInfo_feature_div\")).scrollIntoView("
					+ "new UiSelector().description(\"ADD TO WISH LIST submit.add-to-registry.wishlist\"))"));
			
			//Thread.sleep(5000);
			dr.findElement(By.xpath("//android.view.View[@resource-id='buyNowCheckout']")).click();
			//Thread.sleep(2000);
			dr.findElement(By.xpath("//android.widget.Button[contains(@content-desc, 'Deliver to this address ')]")).click();
			//Thread.sleep(4000);
			dr.findElement(By.xpath("//android.view.View[4][@index='3']")).click();		//Delivery Date
			System.out.println("Date selected");
			//Thread.sleep(2000);
			dr.findElement(By.xpath("//android.view.View[@index='2']/android.widget.Button[contains(@content-desc, 'Continue')]")).click();
			System.out.println("Continue on Delivery Date screen");
			//Thread.sleep(2000);
			dr.findElement(By.xpath("//android.view.View[@resource-id='pp-z7zeN1-68']/android.view.View[4]")).click();	//Netbanking
			System.out.println("Clicked on Netbanking");
			//Thread.sleep(2000);
			dr.findElement(By.xpath("//android.widget.Spinner[@resource-id='pp-z7zeN1-104']")).click();
			//Thread.sleep(2000);
			dr.findElement(By.xpath("//android.view.View[@content-desc='Axis Bank']")).click();
			dr.findElement(By.xpath("//android.widget.Button[@content-desc='Continue']")).click();
			System.out.println("Clicked on Continue on Netbanking screen");
			//Thread.sleep(2000);
			
			
			
			dr.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId('setOrderingPrefsCheckbox'))"
					+ ".scrollIntoView(new UiSelector().description('Sold by: Cloudtail India'))"));
			Thread.sleep(5000);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		
	}

	

	
	

}
