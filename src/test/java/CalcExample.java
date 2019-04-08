import org.openqa.selenium.By;

import java.io.File;

public class CalcExample extends BaseTest{

	public CalcExample(){ super("android");
	}
	
	public CalcExample(int deviceNum) {
		super(deviceNum, "android");
	}
	
	public void performOperations() {
		
		try
		{
			driver.findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
			driver.findElement(By.id("com.android2.calculator3:id/digit5")).click();
			driver.findElement(By.id("com.android2.calculator3:id/plus")).click();
			driver.findElement(By.id("com.android2.calculator3:id/digit9")).click();
			driver.findElement(By.id("com.android2.calculator3:id/equal")).click();
			String num = driver.findElement(By.xpath("//android.widget.EditText[@index=0]")).getText();
			System.out.println("Result : "+num);
			driver.closeApp();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public void run(){
		File app = new File(System.getProperty("user.dir")+"/src/test/resources/AndroidCalculator.apk");
		String appPath = app.getAbsolutePath();
		createAndroidDriver(appPath); // create devices
		performOperations(); // user function
	}

	public static void main(String[] args) {
		// Create object
		CalcExample calc = new CalcExample();
		calc.execute();
	}
}
