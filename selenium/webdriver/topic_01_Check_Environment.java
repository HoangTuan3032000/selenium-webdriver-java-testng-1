package webdriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class topic_01_Check_Environment {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://axiamai.com/users/sign_in");
    }

    @Test
    public void TC_01_Sign_in() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://axiamai.com/users/sign_in");

        WebElement userEmailField = driver.findElement(By.xpath("//input[@id='user_email']"));

        userEmailField.sendKeys("ax21@yopmail.com");

        WebElement userPasswordField = driver.findElement(By.xpath("//input[@id='user_password']"));

        userPasswordField.sendKeys("123456");

        WebElement loginButton = driver.findElement(By.xpath("//button[@class='submit btn dream-btn w-100']"));
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/authenticator/dashboard"));

        // Kiểm tra rằng URL của trang Home đúng
        Assert.assertEquals(driver.getCurrentUrl(), "https://axiamai.com/authenticator/dashboard", "Không chuyển đến trang Home");
    }

    @Test
    public void TC_02_Create_Team_Member() {
        // Nhấn vào avatar để mở dropdown menu
        WebElement avatarButton = driver.findElement(By.xpath("//i[@class='fad fa-user-circle']"));
        avatarButton.click();


        // Tìm và nhấn vào nút 'Teams' bên trong dropdown menu
        WebElement teamButton = driver.findElement(By.xpath("//a[@href='/business/members']"));
        teamButton.click();
    }




    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}