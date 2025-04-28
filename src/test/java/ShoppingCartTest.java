import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class ShoppingCartTest {

    @DataProvider(name = "discounts")
    public Object[][] discounts() {
        return new Object[][] {
                {"148", "4", "52.9", "1420.8"},
                {"1000", "6", "600", "9400"},
                {"9999", "7", "6999.3", "92990.7"},
                {"10225", "8", "8180", "94070"},

        };
    }

    @Test
    public void checkDiscount0(){
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=2&zip_code=12345&first_name=test&" +
                "last_name=test&email=test%40test.test&password1=12345&password2=12345");

        String email = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();

        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value=Login]")).click();

        driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=6");
        driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");

        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("19");
        driver.findElement(By.cssSelector("[value=Update]")).click();

        String discountPercent = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[5]/p/b")).getText();
        String discount$ = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[6]")).getText();
        String total$ = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[7]")).getText();

        softAssert.assertEquals(discountPercent, "0");
        softAssert.assertEquals(discount$, "0");
        softAssert.assertEquals(total$, "190");

        driver.quit();
        softAssert.assertAll();
    }

    @Test(dataProvider = "discounts")
    public void checkDiscount1(String bookCounts, String expectedDiscountPercent,
                               String expectedDiscountDollar, String expectedTotalDollar) throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // не явное ожидание
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=2&zip_code=12345&first_name=test&" +
                "last_name=test&email=test%40test.test&password1=12345&password2=12345");

        String email = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();

        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        //Thread.sleep(5000);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); явное ожидание
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value=Login]")).click();

        driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=4");
        driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");

        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(bookCounts);
        driver.findElement(By.cssSelector("[value=Update]")).click();

        String discountPercent = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[5]/p/b")).getText();
        String discount$ = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[6]")).getText();
        String total$ = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[2]/td[7]")).getText();

        softAssert.assertEquals(discountPercent, expectedDiscountPercent);
        softAssert.assertEquals(discount$, expectedDiscountDollar);
        softAssert.assertEquals(total$, expectedTotalDollar);

        driver.quit();
        softAssert.assertAll();
    }
}
