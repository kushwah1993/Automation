package config;


import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.openqa.selenium.OutputType;

import executionEngine.DriverScript;


public class ActionKeywords {
	
	public static WebDriver driver;
	public static TakesScreenshot screenShot; 
	public static String parentHandle;

	//METHOD TO OPEN BROWSER...
	public static void openBrowser(String actionDescription,String pageObject,String data){
		System.setProperty("webdriver.chrome.driver", "E:\\Automation Jars\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		screenShot=((TakesScreenshot)driver);
		driver.manage().window().maximize();
	}
	
	//METHOD TO NAVIGATE URL...
	public static void navigate(String actionDescription,String pageObject,String data){
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.navigate().to(data);
	}
	
	//METHOD TO CLICK BUTTON...
	public static void click(String actionDescription,String pageObject,String data){
		try{
			driver.findElement(By.xpath(pageObject)).click();
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}
	}
	
	//METHOD TO ENTER THE VALUE IN TEXT FIELD...
	public static void input(String actionDescription,String pageObject,String data){
		try{
			driver.findElement(By.xpath(pageObject)).sendKeys(data);
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}			
	}
	
	
	//METHOD TO MOUSE HOVER ON ANY ELEMENT...
	public static void mouseOver(String actionDescription,String pageObject,String data){
		try{		
			Actions builder=new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath(pageObject))).perform();			
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}				
	}
	
	//METHOD TO MOUSE HOVER AND CLICK ON ANY ELEMENT...
	public static void mouseOverClick(String actionDescription,String pageObject,String data){
		try{
			Actions builder=new Actions(driver);
			builder.moveToElement(driver.findElement(By.xpath(pageObject))).click().build().perform();			
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}
	}
	
	//METHOD IS USED TO DRAG THE SLIDER...
		public static void dragSlider(String actionDescription,String pageObject,String data){
			try{
				int offset=Integer.parseInt(data);
				Actions builder=new Actions(driver);
				//builder.dragAndDropBy(source, xOffset, yOffset)
				builder.dragAndDropBy(driver.findElement(By.xpath(pageObject)),offset,0).build().perform();				
			}
			catch(Exception e){
				System.out.println("Unable To : "+actionDescription);
				DriverScript.bResult=false;
			}					
		}
		
	//METHOD TO MOVE THE CONTROL FROM MAIN PAGE TO IFRAME...
	public static void switchToFrame(String actionDescription,String pageObject,String data){
		try{		
			//Transfer the control using index of iframe...
			driver.switchTo().frame(0);		
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}			
	}
	
	//METHOD TO MOVE THE CONTROL FROM IFRAME TO MAIN PAGE...
	public static void backToMainWindow(String actionDescription,String pageObject,String data){
		try{
			
			driver.switchTo().defaultContent();				
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}			
	}

	//METHOD TO MOVE THE CONTROL FROM PARENT WINDOW TO CHILD WINDOW...
	public static void switchToChildWindow(String actionDescription,String pageObject,String data){
		try{
			parentHandle = driver.getWindowHandle();					
			Set<String>Handles=driver.getWindowHandles();
			for(String childHandle:Handles){
					driver.switchTo().window(childHandle);
					driver.manage().window().maximize();
				}
			}
			catch(Exception e){
				System.out.println("Unable To : "+actionDescription);
				DriverScript.bResult=false;
			}						
		}
	
	//METHOD TO MOVE THE CONTROL FROM CHILD WINDOW TO PARENT WINDOW...
	public static void switchToParentWindow(String actionDescription,String pageObject,String data){
		try{
			driver.switchTo().window(parentHandle);
			}
			catch(Exception e){
				System.out.println("Unable To : "+actionDescription);
				DriverScript.bResult=false;
			}						
		}

	//METHOD TO PICK A DATE FROM CALENDAR...
	public static void datePicker(String actionDescription,String pageObject,String data){
		try{
			List<WebElement> allDates=driver.findElements(By.xpath(pageObject));
			for(WebElement element:allDates)
			{
				String date=element.getText();
				if(date.equalsIgnoreCase(data))
				{
					element.click();
					break;
				}
			}							
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			System.out.println(e);
			DriverScript.bResult=false;
		}
	}
	
	
	//METHOD TO CLICK MULTIPLE TIME ON SAME OBJECT..
	public static void countIncrementer(String actionDescription,String pageObject,String data){
		try{
			int count=Integer.parseInt(data);
			for(int i=1;i<=count;i++)
			{
				driver.findElement(By.xpath(pageObject)).click();
			}									
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}				
	}
	
	//METHOD TO SCROLL TO PARTICULAR OBJECT ON PAGE
	public static void scrollToElement(String actionDescription,String pageObject,String data){
		try{
			JavascriptExecutor jexecutor=(JavascriptExecutor)driver;
			WebElement element=driver.findElement(By.xpath(pageObject));
			jexecutor.executeScript("arguments[0].scrollIntoView(true);",element);
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}				
	}
	
	//METHOD TO ACCEPT THE BROWSER ALERT POP UP
	public static void acceptAlert(String actionDescription,String pageObject,String data){
		try{
			driver.switchTo().alert().accept();
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}
				
	}
	
	//METHOD
	public static void takeScreenShot(String actionDescription){
		try{
			File SrcFile=screenShot.getScreenshotAs(OutputType.FILE);
			File DestFile=new File("D:\\workspace\\Automation_Testing\\defectsScreenshots\\"+actionDescription+".png");
			FileUtils.copyFile(SrcFile, DestFile);
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}
				
	}
			
	public static void waitFor(String actionDescription,String pageObject,String data)throws Exception{
		
		int wait=Integer.parseInt(data);
		Thread.sleep(wait);
	}
	
	public static void closeBrowser(String actionDescription,String pageObject,String data){
		//System.out.println("Successfully Executed");
		driver.close();
		driver.quit();
	}
	
	
	public static void dropdown(String actionDescription,String object,String data){
		try{
			
			Select select=new Select(driver.findElement(By.xpath(object)));
			//select.selectByIndex(1);
			
			select.selectByVisibleText(data);
			
			
		}
		catch(Exception e){
			System.out.println("Unable To : "+actionDescription);
			DriverScript.bResult=false;
		}
		
		
	}
		
	
}
