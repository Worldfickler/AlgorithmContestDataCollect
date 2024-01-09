package org.algotithmcontestdatacollect.crawlerreceiver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerreceiver.Enum.UnRecommendationReason;
import org.algotithmcontestdatacollect.crawlerreceiver.Repositories.*;
import org.algotithmcontestdatacollect.crawlerreceiver.TableEntity.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class CrawlerReceiverApplicationTests {
    @Value("${ChromeDriver}")
    String ChromeDriver;
    @Value("${CodeforcesUserName}")
    String CodeforcesUserName;
    @Value("${CodeforcesPassword}")
    String CodeforcesPassword;
    @Value("${Chrome}")
    String Chrome;
    @Test
    void testChromeDriver() {
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver", ChromeDriver);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true); // 设置无头模式
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setBinary(Chrome);
        driver = new ChromeDriver(chromeOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 设置等待时间为10秒
        String loginUrl = "https://codeforces.com/enter";
        driver.get(loginUrl);
        WebElement handleOrEmail = driver.findElement(new By.ById("handleOrEmail"));
        handleOrEmail.sendKeys(CodeforcesUserName);
        WebElement password = driver.findElement(new By.ById("password"));
        password.sendKeys(CodeforcesPassword);
        WebElement submit = driver.findElement(new By.ByClassName("submit"));
        submit.click();
        driver.close();
    }
}