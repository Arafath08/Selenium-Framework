package comPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class CommonFunctions {
	static FunctionLibrary1 FN1 = new FunctionLibrary1();
	static FunctionLibrary2 FN2 = new FunctionLibrary2();
	static DriverClass DC = new DriverClass();
	public static WebDriver driver;
	String Flag1;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extent;
	// public static TestResults testResults;
	public static String worddoc1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	public static String filename1;
	public static FileOutputStream out;
	public static XWPFDocument doc = new XWPFDocument();
	public static XWPFParagraph p = doc.createParagraph();
	public static XWPFRun r = p.createRun();
	static TakesScreenshot fs = (TakesScreenshot) driver;
	public static int ScreenshotNum = 1;
	public static String currentDir;
	public static String SQLQuery;
	public static String bstatus[][];
	public static ExtentTest test;

	static int LastUsedRow;
	protected static int i = 0;
	Row row;
	Cell cell;
	protected static File directory;
	protected static String filename;

	public void statusReport() throws IOException {
		currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("/", "\\");
		String Keyword = DriverClass.KeyWordinAction;
		String dest = currentDir + "\\Results\\" + Keyword + "\\" + Keyword + "HtmlReport.html";
		File destination1 = new File(currentDir + "\\Results\\" + Keyword);
		destination1.mkdir();
		htmlreporter = new ExtentHtmlReporter(dest);
		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		test = extent.createTest(Keyword, DriverClass.TestDesc);
		test.log(Status.INFO, "Testcase: " + Keyword + "-started");

	}

	public void endReport() {
		extent.flush();
		System.out.println("endreport");
	}

	public static void HtmlReport(String TestDescription, String TestStepStatus) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		// get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date);
		date1 = date1.replace("-", "_");
		date1 = date1.replace("/", "");
		date1 = date1.replace(" ", "");
		date1 = date1.replace(":", "");
		// String ScreenshotNumstring = Integer.toString(ScreenshotNum);
		String ScreenshotName = "Screenshot" + date1;
		if (TestStepStatus.toUpperCase().equals("PASS")) {
			String ScreenshotPath = Capturescreenshot(ScreenshotName);
			System.out.println(ScreenshotPath);
			test.pass(TestDescription, MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotPath).build());
			ScreenshotNum = ScreenshotNum + 1;
			System.out.println("htmlpass");

		} else if (TestStepStatus.toUpperCase().equals("FAIL")) {
			String ScreenshotPath = Capturescreenshot(ScreenshotName);
			test.fail(TestDescription, MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotPath).build());
			ScreenshotNum = ScreenshotNum + 1;
		}

		else {
			test.log(Status.INFO, TestDescription);
		}
	}

	public static String DataTableValue(String ColumnName) {
		try {
			int RowNum = DriverClass.RowInAction;
			ColumnName = ColumnName.toUpperCase();
			// System.out.println(filename);
			File file = new File(System.getProperty("user.dir") + "\\DataSheet\\SeleniumTest.xls");

			FileInputStream fs = new FileInputStream(file);
			Workbook wb = new HSSFWorkbook(fs);
			Sheet sheet = wb.getSheet("DataSheet");
			Row row;
			int rows;
			rows = sheet.getPhysicalNumberOfRows();
			int cols = 1;
			int tmp = 0;
			for (int i = 0; i < 5 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols) {
						cols = tmp;
					}
					row = sheet.getRow(0);
					for (int c = 1; c < cols; c++) {
						String cell1 = row.getCell(c).toString().toUpperCase();
						if (cell1.equals(ColumnName)) {
							row = sheet.getRow(RowNum);
							String cell11 = row.getCell(c).toString();
							// System.out.println(cell11);
							c = cols;
							return cell11;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while retrieving screenshots" + e.getMessage());
			return "";
		}
		return ColumnName;
	}

	public static String Capturescreenshot(String screenshotName) {
		try {
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			Date date3 = date;
			String date1 = String.valueOf(date);
			String date2 = String.valueOf(date3);
			date1 = date1.replace("-", "_");
			date2 = date2.replace("/", "-");
			date2 = date2.replace(" ", "-");
			date2 = date2.replace(":", "-");
			TakesScreenshot ts = (TakesScreenshot) driver;
			File Source = ts.getScreenshotAs(OutputType.FILE);
			String Keyword = DriverClass.KeyWordinAction;
			String dest = currentDir + "\\Results\\" + Keyword + "\\Screenshots" + date1 + "\\" + screenshotName
					+ ".png";
			File destination = new File(dest);
			FileUtils.copyFile(Source, destination);
			return dest;
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("Exception while taking screenshot" + e.getMessage());
			return null;
		}
	}

	public void functioncall(String d) throws FileNotFoundException, IOException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		if (d != "") {
			try {
				Method getMethod = FN1.getClass().getMethod(d.trim());
				System.out.println(getMethod);
				getMethod.setAccessible(true);
				getMethod.invoke(getMethod);
				Flag1 = "Pass";

			} catch (NoSuchMethodException e) {
				System.out.println("No Such Function in FN1");
				Flag1 = "Fail";
			}
			if (Flag1.equals("Pass")) {
			} else {
				try {
					Method getMethod = FN2.getClass().getMethod(d);
					getMethod.setAccessible(true);
					getMethod.invoke(getMethod);
					Flag1 = "Pass";
				} catch (NoSuchMethodException e) {
					Flag1 = "Fail";
				}
			}
			if (Flag1.equals("Pass")) {

			} else {
				System.out.println("No Such Function in Function Lib 1&2");
			}
		}
	}

	public static void reportstep(String desc, String status) throws InvalidFormatException {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();
		try {
			filename1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			Date date3 = date;
			String date1 = String.valueOf(date);
			String date2 = String.valueOf(date3);
			date1 = date1.replace("-", "_");
			date2 = date2.replace("/", "_");
			date2 = date2.replace("", "_");
			date2 = date2.replace(":", "_");

			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String keyword = DriverClass.KeyWordinAction;
			String dest = currentDir + "Results\\" + keyword + "\\Screenshots" + date1 + "\\" + desc + ".png";

			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			String imgFile = dest;
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			r.addBreak();
			r.setText(desc);
			r.addBreak();

			r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(500), Units.toEMU(500));
			out = new FileOutputStream("Results\\" + keyword + "\\WORD1.docx");
			doc.write(out);

		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this function is to verify the exist of an element if yes return true or
	// false, within the given seconds

	public static boolean Exist(String id, String idValue, Integer Seconds) {
		switch (id.toString().trim().toUpperCase()) {
		case "ID":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.id(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "NAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.name(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "XPATH":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "LINKTEXT":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "PARTIALLINKTEXT":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "TAGNAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.tagName(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "CLASSNAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.className(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "CSSSELECTOR":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(idValue)));
				return true;
			} catch (Exception ex) {
				return false;

			}

		}
		return false;

	}

	// This is for same exist but without seconds - default 60seconds applied

	public static boolean Exist(String id, String idValue) {
		Integer Seconds = 60;
		switch (id.toString().trim().toUpperCase()) {
		case "ID":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.id(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "NAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.name(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "XPATH":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "LINKTEXT":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "PARTIALLINKTEXT":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}
		case "TAGNAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.tagName(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "CLASSNAME":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.className(idValue)));
				return true;
			} catch (Exception ex) {
				return false;
			}

		case "CSSSELECTOR":
			try {
				WebDriverWait wait = new WebDriverWait(driver, Seconds);
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(idValue)));
				return true;
			} catch (Exception ex) {
				return false;

			}

		}
		return false;

	}

	// This function is to click on a particular element

	public static boolean ClickOnElement(String id, String idValue) {
		// Integer Seconds=60;

		if (Exist(id, idValue, 60)) {
			switch (id.toString().trim().toUpperCase()) {
			case "ID":
				try {
					driver.findElement(By.id(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.id(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "XPATH":
				try {
					driver.findElement(By.xpath(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.xpath(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "NAME":
				try {
					driver.findElement(By.name(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.name(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "LINKTEXT":
				try {
					driver.findElement(By.linkText(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.linkText(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "PARTIALLINKTEXT":
				try {
					driver.findElement(By.partialLinkText(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.partialLinkText(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "TAGNAME":
				try {
					driver.findElement(By.tagName(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.tagName(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}

			case "CLASSNAME":
				try {
					driver.findElement(By.className(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.className(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}
			case "CSSSELECTOR":
				try {
					driver.findElement(By.cssSelector(idValue)).click();
					return true;
				} catch (Exception ex) {
					try {
						driver.findElement(By.cssSelector(idValue)).sendKeys("MSL");
						return true;
					} catch (Exception ex1) {
						return false;
					}

				}

			}
			return false;
		} else {
			return false;
		}

	}
	
	

}
