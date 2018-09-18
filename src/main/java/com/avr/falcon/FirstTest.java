package com.avr.falcon;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class FirstTest {

    public static void main(String[] argts) {

        String driverloc = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", driverloc + "/libs/chromedriver/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.allmusic.com/");
        String title = driver.getTitle();
        List<WebElement> allFormChildElements = driver.findElements(By.xpath("//form[@name='site-search']/*"));


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
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }



}
