package appiumTest;

import helper.*;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest implements Runnable{
	protected IOSDriver iOSDriver;
	protected AndroidDriver driver;

	protected String deviceId;
	protected String deviceName;
	protected String osVersion;
	protected String port;
	protected Thread t;
	protected int deviceCount;
	
	AppiumManager appiumMan = new AppiumManager();
	static Map<String, String> devices = new HashMap<String, String>();
	static DeviceConfiguration deviceConf = new DeviceConfiguration();

	public BaseTest(String platform){
		try {
			System.out.println(platform);
			if(platform.equalsIgnoreCase("android")) {
				devices = deviceConf.getAndroidDevices();
				deviceCount = devices.size() / 3;
			}else if(platform.equalsIgnoreCase("ios")) {
				System.out.println("***********iOS Devices************");
				devices = deviceConf.getiOSDevices();
				deviceCount = devices.size() / 2;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BaseTest(int i, String platform){
		int deviceNumber;
		if(platform.equalsIgnoreCase("android"))
			 deviceNumber = (i+1);
		else
			deviceNumber = (i+0);
		this.deviceId = devices.get("deviceID"+deviceNumber);
		this.deviceName = devices.get("deviceName"+deviceNumber);
		this.osVersion = devices.get("osVersion"+deviceNumber);

		System.out.println(this.deviceId +" "+ this.deviceName+" "+this.osVersion);
	}
	
	public void createAndroidDriver(){
		try	{
			port=appiumMan.startAppium(); 			// Start appium server
			System.out.println("Starting Appium at port- "+port);
			  
			// create appium driver instance
			DesiredCapabilities capabilities = DesiredCapabilities.android();
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformName", "android");
			capabilities.setCapability(CapabilityType.VERSION, osVersion);
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			capabilities.setCapability("udid", deviceId);
				
			this.driver = new AndroidDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"),capabilities);
		}
		catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public void createAndroidDriver(String appPath){
		try	{
			port = appiumMan.startAppium(); 			// Start appium server			  
			  
			// create appium driver instance
			DesiredCapabilities capabilities = DesiredCapabilities.android();
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformName", "android");
			capabilities.setCapability(CapabilityType.VERSION, osVersion);
			capabilities.setCapability("app", appPath);
			capabilities.setCapability("udid", deviceId);

			this.driver = new AndroidDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"),capabilities);
			System.out.println("**********App Launched$$$$$$$$$$$$$$$$$$$");
		}
		catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	public IOSDriver<MobileElement> createIOSDriver()
			throws IOException, InterruptedException {
		try {
			port = appiumMan.startAppium(); 			// Start appium server

			System.out.println("Intialising IOS Driver");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("deviceName", "iphone 8");
			capabilities.setCapability("platformVersion", "11.4");
			capabilities.setCapability("bundleId", "com.milwaukeetool.mymilwaukee");
			capabilities.setCapability("newCommandTimeout",9000);
			capabilities.setCapability("useNewWDA", true);
			capabilities.setCapability("showXcodeLog", true);
			capabilities.setCapability("udid", deviceId);
			capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			this.iOSDriver = new IOSDriver(
					new URL("http://127.0.0.1:"+port+"/wd/hub"),capabilities);
			System.out.println("App Launch Successful");
			return this.iOSDriver;
		} catch (Exception e) {
			System.out.println("Failed to getIOSDriver, "+ e.fillInStackTrace());
		}
		return this.iOSDriver;
	}


	public void destroyDriver()
	{
		driver.quit();
		try {
			deviceConf.stopADB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void start(){
		if (t == null){
		  t = new Thread(this);
		  t.start ();
		}
	}

	public void run(){
	}

	public  <c> void execute()
	{
		Class<?> c;
		try {
			int startMethod = 0;
			String className = this.getClass().toString();
			System.out.println("class : "+className);
			className = className.replace("class ", "");
			System.out.println("class : "+className);
			// Get extended class name
			c = Class.forName(className);
			System.out.println("class : "+c);

			// Get start method
			Method[] m = c.getMethods();
			System.out.println("methods: "+m.length);
			for(int i=0;i<m.length;i++)	{
				//System.out.println("methods: "+m[i]);
				if(m[i].toString().contains("start")){
					startMethod=i;
					break;
				}
			}
			System.out.println("methods: "+m[startMethod]);
			// get constructor
			Constructor<?> cons = c.getConstructor(Integer.TYPE);
			System.out.println("cons: "+cons);

			System.out.println("deviceCount: "+deviceCount);
			// Create array of objects
			Object obj =  Array.newInstance(c, deviceCount);
			for (int i = 0; i < deviceCount; i++) {
                Object val = cons.newInstance(i);
                Array.set(obj, i, val);
            }

			for (int i = 0; i < deviceCount; i++) {
                Object val = Array.get(obj, i);
                m[startMethod].invoke(val);
            }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
