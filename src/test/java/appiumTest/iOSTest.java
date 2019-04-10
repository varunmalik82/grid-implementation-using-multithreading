package appiumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class iOSTest extends BaseTest {

    public iOSTest(){super("ios");
    }

    public iOSTest(int deviceNum){
        super(deviceNum, "ios");
    }

    public void login(){
        try{

            WebDriverWait wait = new WebDriverWait(iOSDriver, 30);
            Thread.sleep(5000);
            iOSDriver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"SignInButton\"]")).click();
            iOSDriver.findElement(By.xpath("//XCUIElementTypeCell[@name=\"login_email_text_field\"]/XCUIElementTypeTextField")).sendKeys("vmalik@dminc.com");
            iOSDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"Password\"]")).sendKeys("miP4cvma");
            iOSDriver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"login_sign_in_button\"]")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"settingsTab\"]"))).click();

            iOSDriver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"settingsTab\"]")).click();

            JavascriptExecutor js = (JavascriptExecutor) iOSDriver;
            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            js.executeScript("mobile: swipe", params);

            iOSDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"logout_button\"]")).click();



            Thread.sleep(2000);
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

        try {
            createIOSDriver(); // create devices
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        login(); // user function
        composeEmail();
        logout();
        //destroyDriver(); // destroy devices
    }

    public static void main(String[] args) {
        // Create object
        iOSTest iosTest = new iOSTest();

        // Get connected device count
        int totalDevices=iosTest.deviceCount;

        // Initialize threads for each connected devices
        iOSTest[] threads = new iOSTest[totalDevices];

        // Create threads for each connected devices
        for(int i=0;i<totalDevices;i++)
            threads[i] = new iOSTest(i);

        // Start running execution on each device
        for(int i=0;i<totalDevices;i++)
            threads[i].start();
    }
}

