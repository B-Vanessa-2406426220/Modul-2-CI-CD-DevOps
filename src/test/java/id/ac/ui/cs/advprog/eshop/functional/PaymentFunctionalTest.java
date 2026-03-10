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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class PaymentFunctionalTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void testPaymentPageIsAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/create");
        String headerText = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Create Payment", headerText);
    }

    @Test
    void testOrderPayPageIsAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/pay");
        String headerText = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Pay Order", headerText);
    }
}