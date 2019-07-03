package comPackage2;

public class FunctionLibrary2 extends CommonFunctions{
	//FunctionLibrary1 fn1=new FunctionLibrary1();
	//use fn1 to access all objects and functions in FunctionLibrary1.class file

	public static void Function2()
	{
		System.out.println("Test dh");
	}
	
	public static void Close_Browser()
	{
		System.out.println("Closing all browsers");
		driver.close();
		driver.quit();
	}
}
