package comPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.testng.annotations.Test;

public class DriverClass {
	public static String DataSheet, KeyWordinAction, TestDesc;
	public static int RowInAction;
	public int bstatus;
	static CommonFunctions CM = new CommonFunctions();
	


	@Test
	public void driverMethod() throws FileNotFoundException, IOException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		
		
		
		String dSheet = System.getProperty("user.dir") + "\\DataSheet\\";
		String sFile = "SeleniumTest.xls";
		System.out.println("Test Started");
		// Code for reading excel & running functions

		System.out.println(dSheet + sFile);
		
		String source=(System.getProperty("user.dir")+"\\src\\comPackage\\FunctionLibrary1.java");
		String dest=(System.getProperty("user.dir")+"\\src\\comPackage2\\FunctionLibrary1.java");
		//copy(source,dest);
		/*FileUtils.copyFile(source, dest);
		System.out.println("Copied");*/
		
		File file = new File(dSheet + sFile);

		FileInputStream fs = new FileInputStream(file);
		Cell cell;
		Workbook wb = new HSSFWorkbook(fs);
		Sheet sheet = wb.getSheetAt(0);
		Row row;
		int rows;
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 1;
		int tmp = 0;
		for (int i = 0; i <= 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if (row != null) {

				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		for (int r = 0; r < rows; r++) {
			row = sheet.getRow(r);
			if (row != null) {
				String vheck = "YES";
				String cell1 = row.getCell(1).toString().trim().toUpperCase();
				KeyWordinAction = row.getCell(0).toString();
				TestDesc = row.getCell(2).toString();
				String parallelTest=row.getCell(3).toString();
				
				if (cell1.equals(vheck) && parallelTest.equals("1")) {
					CM.statusReport();
					for (int c = 4; c < cols; c++) {
						cell = row.getCell(c);
						if (cell != null) {
							String a1 = cell.toString();
							String str = a1;
							String[] arrofStr = str.split(":");
							if (arrofStr.length >= 2) {
								RowInAction = Integer.valueOf(arrofStr[1]);
							} else {
								RowInAction = 1;
							}
							try {
								System.out.println(arrofStr[0]);
								CM.functioncall(arrofStr[0]);
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							}
							CM.endReport();

						}
					}
				}
			}
		}

		System.out.println("Test Ended");
	}
	
public void copy(String source,String dest) throws IOException
{

	  BufferedWriter out1 = new BufferedWriter(new FileWriter(dest));    
    BufferedReader in1 = new BufferedReader(new FileReader(source));
    String strCopy;  
    while ((strCopy = in1.readLine()) != null) {
       if(strCopy.contains("package comPackage;")) {
      	 out1.write("package comPackage2;\n");
       }
      	 else
      	 {
      		 out1.write(strCopy+"\n");
      	 }

       }
    out1.close();
    in1.close();
  System.out.println("Copied");
	
}
	     

	
}


