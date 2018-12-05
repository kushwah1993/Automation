package executionEngine;

import config.ActionKeywords;
import utility.ExcelUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class DriverScript {
	public static String sActionDescription;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sDataSet;
	public static Method method[];
	public static ActionKeywords actionKeywords;
	public static boolean bResult;
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseId;
	public static String sRunMode;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
    public static ExtentTest test;
    public static String sTestCaseDescription;
	public static String reportPath ="E:\\workspace\\Automation_Testing\\Automation_Testing\\myExtentReport.html";
	public static String testCasesPath ="E:\\workspace\\Automation_Testing\\Automation_Testing\\src\\dataEngine\\DataEngine.xlsx";
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}

	public static void main(String[] args) throws Exception{
		
//		SCRIPTING START....E:\workspace\Automation_Testing\Automation_Testing
		htmlReporter = new ExtentHtmlReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		
		ExcelUtils.setExcelFile(testCasesPath);		
		DriverScript startEngine=new DriverScript();
		startEngine.executeTestCase();				
		}
	
			
	private void executeTestCase()throws Exception{
		bResult=true;
		
		
		int totalTestCases=ExcelUtils.getRowCount("TestCases")-1;
		for(int iTestCase=1;iTestCase<=totalTestCases;iTestCase++)	//Execute number of times equal to Test Cases...
		{
 			sTestCaseId=ExcelUtils.getCellData(iTestCase, 0,"TestCases");
			sTestCaseDescription=ExcelUtils.getCellData(iTestCase, 1,"TestCases");
			sRunMode=ExcelUtils.getCellData(iTestCase,2,"TestCases");
			
			test=extent.createTest(sTestCaseDescription); //Creating the Extent test case...
			
			if(sRunMode.equals("Yes")){					  //If Run Mode is Yes...
		
				iTestStep=ExcelUtils.getRowContains(sTestCaseId, 0,"TestSteps");
				iTestLastStep=ExcelUtils.getTestStepsCount(iTestStep, sTestCaseId, "TestSteps");
			int flag=0;
			for(;iTestStep<=iTestLastStep;iTestStep++)
			{
				sActionDescription=ExcelUtils.getCellData(iTestStep,2,"TestSteps");
				sPageObject=ExcelUtils.getCellData(iTestStep,3,"TestSteps");
				sActionKeyword=ExcelUtils.getCellData(iTestStep,4,"TestSteps");
				sDataSet=ExcelUtils.getCellData(iTestStep,5,"TestSteps");	
				
				
				if(flag==0)
				{
					executeActions();
					if(bResult==true){
						ExcelUtils.setCellData(iTestStep,6,"Pass","TestSteps");	
						test.log(Status.PASS,sActionDescription);						
					}
					else{
						ExcelUtils.setCellData(iTestStep,6,"Fail","TestSteps");
						test.log(Status.FAIL,sActionDescription);
						flag=1;						
						//break;	uncomment it if stop the test case execution when TEST STEP is fail
					}	
				}
				else{
					test.log(Status.SKIP,sActionDescription);
				}							
							
			}	
			if(bResult==true){
				ExcelUtils.setCellData(iTestCase,3,"Pass","TestCases");	
				
			}
			else{
				ExcelUtils.setCellData(iTestCase,3,"Fail","TestCases");
				ActionKeywords.closeBrowser("","", "");
				
				break;			//uncomment it if stop the test case execution when TEST CASE is fail
				
			}	
			
			}				//If Run Mode is Yes close....
			
			
			
		//Extra code for no run mode....	
			if(sRunMode.equals("No")){
				//****************delete if not required...
				
//				iTestStep=ExcelUtils.getRowContains(sTestCaseId, 0,"TestSteps");
//				iTestLastStep=ExcelUtils.getTestStepsCount(iTestStep, sTestCaseId, "TestSteps");
//				for(;iTestStep<=iTestLastStep;iTestStep++)
//				{
//					sActionDescription=ExcelUtils.getCellData(iTestStep,2,"TestSteps");
//					sActionKeyword=ExcelUtils.getCellData(iTestStep,4,"TestSteps");
					//test.log(Status.SKIP,sActionDescription);	
//								
//				}					
				//****************delete if not required...
				
				test.skip(sTestCaseDescription);
				
			}
			
			
		}
		extent.flush();
	}	
		
		private static void executeActions(){
			for(int i=0;i<method.length;i++){		
				if(method[i].getName().equals(sActionKeyword)){
					try {
						method[i].invoke(actionKeywords,sActionDescription,sPageObject,sDataSet);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						break;										
						}
				}
		}
		
		

//		SCRIPTING CLOSE....		
		
//		=================DON'T EDIT THIS CODE====================	
			
//		code for Explicit timeout...
//		try{
//			
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='mainMenu']/li[3]/a"))).click();
//		}
//		catch(Exception E){
//			System.out.println("Element not found");
//		}
//		driver.close();
		
//		======================META DATA==========================		
		
		
		
		
		
	}


