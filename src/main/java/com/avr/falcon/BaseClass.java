package com.avr.falcon;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.List;

public class SuperClass {

public static final int kSTATUS_SUCCESS = 0;
public static final int kSTATUS_NO_DRIVER = 1;
public static final int kSTATUS_NO_BINARY = 2;


    static String mBrowser;
    static WebDriver mDriver;




    public static void main(String[] argts) {

        setBrowser();
        int result = getDriver(mBrowser);

        if (result == kSTATUS_SUCCESS) {
            performTests();
        } else if (result == kSTATUS_NO_DRIVER) {
            System.out.println("Unable to continue, no driver found for browser: " + mBrowser);
            new Exception();
        } else if (result == kSTATUS_NO_BINARY) {
            System.out.println("Unable to continue, no binary found for browser: " + mBrowser);
            new Exception();
        }
    }

    /**
     *  get browser information from properties file
     */
    static void setBrowser() {
        mBrowser = "Firefox";
    }

    static int getDriver(String browser) {
        int returnStatus = kSTATUS_SUCCESS;

        String projectRoot =  System.getProperty("user.dir");
        String libsRoot = projectRoot + "/libs";

        if (browser.toLowerCase().contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", libsRoot + "/chromedriver/chromedriver");

            try {
                mDriver = new ChromeDriver();
            } catch(IllegalStateException ex) {
                returnStatus = kSTATUS_NO_DRIVER;
            } catch(WebDriverException ex) {
                returnStatus = kSTATUS_NO_BINARY;
            }

        } else if (browser.toLowerCase().contains("firefox")) {
            // try `gecko` driver explicitly
            System.setProperty("webdriver.gecko.driver", libsRoot + "/geckodriver/geckodriver");

            try {
                mDriver = new FirefoxDriver();
            } catch(IllegalStateException ex) {
                returnStatus = kSTATUS_NO_DRIVER;
            } catch(WebDriverException ex) {
                returnStatus = kSTATUS_NO_BINARY;
            }

            // try `marionette` driver explicitly
            if (returnStatus > kSTATUS_SUCCESS) {
                System.setProperty("webdriver.firefox.marionette", libsRoot + "/geckodriver/geckodriver");
                returnStatus = kSTATUS_SUCCESS;

                try {
                    mDriver = new FirefoxDriver();
                } catch(IllegalStateException ex) {
                    returnStatus = kSTATUS_NO_DRIVER;
                } catch(WebDriverException ex) {
                    returnStatus = kSTATUS_NO_BINARY;
                }
            }

        } else if (browser.toLowerCase().contains("safari")) {

            try {
                mDriver = new SafariDriver();
            } catch(IllegalStateException ex) {
                returnStatus = kSTATUS_NO_DRIVER;
            } catch(WebDriverException ex) {
                returnStatus = kSTATUS_NO_BINARY;
            }
            System.out.println();

        } else if (browser.toLowerCase().contains("opera")) {
            System.setProperty("webdriver.opera.driver", libsRoot + "/operadriver/operadriver");

            try {
                mDriver = new OperaDriver();
            } catch(IllegalStateException ex) {
                returnStatus = kSTATUS_NO_DRIVER;
            } catch(WebDriverException ex) {
                returnStatus = kSTATUS_NO_BINARY;
            }
            System.out.println();
        }

        return returnStatus;
    }

    static void performTests() {

        mDriver.get("http://www.allmusic.com/");
        String title = mDriver.getTitle();
        List<WebElement> allFormChildElements = mDriver.findElements(By.xpath("//form[@name='site-search']/*"));


        WebElement btnSearch = null;
        WebElement tfSearch = null;

        for (WebElement item : allFormChildElements) {
            if (item.getTagName().equals("input")) {
                String typ = item.getAttribute("type");
                if (typ.equals("submit")) {
                    System.out.println("submit button");
                    btnSearch = item;

                } else if (typ.equals("search")) {
                    System.out.println("search item");
                    tfSearch = item;
                }
            }
//            else if (item.getTagName().equals("select"))
//            {
//                //select an item from the select list
//            }
        }


        tfSearch.sendKeys("Stray Cats");
//        btnSearch.click();

        tfSearch.sendKeys(Keys.DOWN);
        tfSearch.sendKeys(Keys.RETURN);

        System.out.println();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDriver.quit();

    }
}
