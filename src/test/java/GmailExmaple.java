public class GmailExmaple extends BaseTest{

	public GmailExmaple(){super("android");
	}
	
	public GmailExmaple(int deviceNum){
		super(deviceNum, "android");
	}
	
	public void login(){
		try{
			iOSDriver.get("http://gmail.com/");
			Thread.sleep(2000);
			/*driver.findElement(By.id("Email")).sendKeys("test");
			Thread.sleep(2000);
			driver.findElement(By.id("Passwd")).sendKeys("test");
			Thread.sleep(2000);
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(2000); */
			driver.quit();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public void composeEmail(){
		System.out.println("**********Composing email....");
	}

	public void logout(){
		System.out.println("***********logging out....");
	}
	
	public void run(){

		createAndroidDriver(); // create devices
		login(); // user function
		composeEmail();
		logout();
		//destroyDriver(); // destroy devices
	}
	
	public static void main(String[] args) {
		// Create object
		GmailExmaple gmail = new GmailExmaple();

		// Get connected device count
		int totalDevices=gmail.deviceCount;
		
		// Initialize threads for each connected devices
		GmailExmaple[] threads = new GmailExmaple[totalDevices];
		
		// Create threads for each connected devices
		for(int i=0;i<totalDevices;i++)
			threads[i] = new GmailExmaple(i);
		
		// Start running execution on each device
		for(int i=0;i<totalDevices;i++)
			threads[i].start();
	}
}
