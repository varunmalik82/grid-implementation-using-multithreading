package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTest implements Runnable {

    WebDriver driver;

    public void run(){
        openBrowser();
        login();
        logout();
        driver.quit();
    }

    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver=new ChromeDriver();
        driver.navigate().to("https://mail.google.com");
    }

    public void login(){
        System.out.println("Login Successful");
    }

    public void logout(){
        System.out.println("Logout Successful");
    }


}
