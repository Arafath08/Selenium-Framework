package comPackage;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
public class RunHere {
	
	

	public static void main(String[] args) {

	TestNG runner=new TestNG();

	List<String> suitefiles=new ArrayList<String>();

	suitefiles.add("C:\\Selenium\\com-Selenium\\testng2.xml");
	

	runner.setTestSuites(suitefiles);

	//runner.run();
	
	TestNG runner1=new TestNG();
	
	List<String> suitefiles1=new ArrayList<String>();

	suitefiles1.add("C:\\Selenium\\com-Selenium\\testng.xml");

	runner1.setTestSuites(suitefiles1);

	runner1.run();
		
	
	}
	
}
