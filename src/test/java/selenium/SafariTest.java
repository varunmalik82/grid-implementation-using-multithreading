package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariTest implements Runnable {

        WebDriver driver;

        public void run(){
            openBrowser();
            login();
            logout();
            driver.close();
        }

        public void openBrowser(){
            driver = new SafariDriver();
            driver.navigate().to("https://mail.google.com");
            System.out.println("Safari browser Launched");
        }

        public void login(){
            System.out.println("Login Successful");
        }

        public void logout(){
            System.out.println("Logout Successful");
        }

}
