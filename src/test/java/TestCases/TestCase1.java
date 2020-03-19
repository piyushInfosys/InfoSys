package TestCases;



import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import CommonFunctions.*;

public class TestCase1 {
	
	UtilityAndSupport usp = new UtilityAndSupport();
	public final static Logger logger = Logger.getLogger("rootLogger");
	
	WebDriver dr;
	
	@BeforeTest
	public void setupAndLogin() {
		System.out.println("This is before test annotation...");
		logger.info("This the Log file text to be printed in the logfile");
		
		try {
			usp.loadpropertiesFIle();
			dr = usp.setRequiredCapabilities();
			
			usp.loginToApp(dr);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void OperationsOn65InchTV() throws InterruptedException {
	try {
		usp.searchAndSelectTV(dr);
		
		usp.getTVdetails(dr);
		
	} catch (Exception ex) {
		ex.printStackTrace();
	}
		
		
	}
	
	
	
	

}
