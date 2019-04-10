package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Runner {

    static Thread chromeThread = new Thread(new ChromeTest() {
        @Override
        public void run() {
            super.run();
        }
    });

    static Thread safariThread = new Thread(new SafariTest() {
        @Override
        public void run() {
            super.run();
        }
    });

    public static void main(String[] args) {
        chromeThread.start();
        safariThread.start();
    }

}

class SafariTest implements Runnable {

    WebDriver driver;

    public void run() {
        openBrowser();
        login();
        logout();
        driver.close();
    }

    public void openBrowser() {
        driver = new SafariDriver();
        driver.navigate().to("https://mail.google.com");
        System.out.println("Safari browser Launched");
    }

    public void login() {
        System.out.println("Safari - Login Successful");
    }

    public void logout() {
        System.out.println("Safari - Logout Successful");
    }

}

class ChromeTest implements Runnable {

    WebDriver driver;

    public void run() {
        openBrowser();
        login();
        logout();
        driver.quit();
    }

    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("https://mail.google.com");
        System.out.println("Chrome browser Launched");
    }

    public void login() {
        System.out.println("Chrome - Login Successful");
    }

    public void logout() {
        System.out.println("Chrome - Logout Successful");
    }

}
