package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class OrderFunctionalTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void testCreateOrderPageIsAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");
        String headerText = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Create New Order", headerText);
    }

    @Test
    void testOrderHistoryPageIsAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");
        String headerText = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Search Order History", headerText);
    }

    @Test
    void testCreateOrderProcess(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");
        driver.findElement(By.id("author")).sendKeys("Safira Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("/history") ||
                driver.getPageSource().contains("Safira Sudrajat"));
    }
}