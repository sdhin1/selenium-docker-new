package com.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	protected WebDriver driver;
	
    @BeforeTest
    @Parameters({"executionType"})
    public void setUpDriver(String executionType, ITestContext ctx) throws MalformedURLException {
    	
    	String host = "localhost";
        DesiredCapabilities dc;
        
        System.out.println("------------- Executing tests on "+executionType.toUpperCase()+"---------------");
    	
    	if(executionType.equalsIgnoreCase("local")) {
    		
    		WebDriverManager.chromedriver().setup();
            this.driver = new ChromeDriver();
            this.driver.manage().window().maximize();
            
    	} else {

	        if(System.getProperty("BROWSER") != null &&
	                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
	        	
	            dc = DesiredCapabilities.firefox();
	            
	        } else {
	        	
	        	dc = DesiredCapabilities.chrome();
	        	
	        }
	
	        if(System.getProperty("HUB_HOST") != null){
	            host = System.getProperty("HUB_HOST");
	        }
	
	        String testName = ctx.getCurrentXmlTest().getName();
	
	        String completeURL = "http://" + host + ":4444/wd/hub";
	
	        dc.setCapability("name", testName);
	        this.driver = new RemoteWebDriver(new URL(completeURL), dc);
	
	    }
    }
    
	@AfterTest
    public void quitBrowser(){
        this.driver.quit();
    }

}
