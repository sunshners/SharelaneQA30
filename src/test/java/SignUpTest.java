import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SignUpTest {
    @Test
    public void checkSignUpValidData() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();

        boolean isDisplayed = driver.findElement(By.cssSelector("[value='Register']")).isDisplayed();
        Assert.assertTrue(isDisplayed);

        driver.findElement(By.name("first_name")).sendKeys("Sasha");
        driver.findElement(By.name("last_name")).sendKeys("Gold");
        driver.findElement(By.name("email")).sendKeys("gold@sasha.com");
        driver.findElement(By.name("password1")).sendKeys("123123");
        driver.findElement(By.name("password2")).sendKeys("123123");
        driver.findElement(By.cssSelector("[value='Register']")).click();

        String accountCreated = driver.findElement(By.className("confirmation_message")).getText();
        Assert.assertEquals(accountCreated, "Account is created!");
    }

}
