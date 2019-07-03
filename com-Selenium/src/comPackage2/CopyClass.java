package comPackage2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

@Test
public class CopyClass {
	
	public void CopyFunction() throws IOException {
		String dSheet = System.getProperty("user.dir") + "\\DataSheet\\";
	
	String sFile = "SeleniumTest.xls";
	System.out.println("Test Started");
	// Code for reading excel & running functions

	System.out.println(dSheet + sFile);
	
	String source=(System.getProperty("user.dir")+"\\src\\comPackage\\FunctionLibrary1.java");
	String dest=(System.getProperty("user.dir")+"\\src\\comPackage2\\FunctionLibrary1.java");
	copy(source,dest);
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
