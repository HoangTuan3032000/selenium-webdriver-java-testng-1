package webdriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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

public class topic01CheckEnvironment {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://axiamai.com/users/sign_in");
    }

    @Test
    public void TC_01_Url() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://axiamai.com/users/sign_in");
    }

    @Test
    public void TC_02_TypeUserEmail() {
        WebElement userEmailField = driver.findElement(By.xpath("//input[@id='user_email']"));

        userEmailField.sendKeys("ax21@yopmail.com");
    }

    @Test
    public void TC_03_TypeUserPassword() {
        WebElement userPasswordField = driver.findElement(By.xpath("//input[@id='user_password']"));

        userPasswordField.sendKeys("123456");
    }

    @Test
    public void TC_04_ClickSignInButton() {
        WebElement loginButton = driver.findElement(By.xpath("//button[@class='submit btn dream-btn w-100']"));
        loginButton.click();
    }
    @Test
    public void TC_05_CheckHomePageAfterLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/authenticator/dashboard"));

        // Kiểm tra rằng URL của trang Home đúng
        Assert.assertEquals(driver.getCurrentUrl(), "https://axiamai.com/authenticator/dashboard", "Không chuyển đến trang Home");
    }

    @Test
    public void TC_06_CheckSuccessNotification() {
        // Sử dụng WebDriverWait với thời gian chờ 30 giây
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Chờ đợi thông báo "Signed in successfully." xuất hiện
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Signed in successfully.')]")
        ));

        // Xác minh rằng thông báo đã xuất hiện
        Assert.assertTrue(successMessage.isDisplayed(), "Thông báo 'Signed in successfully.' không hiển thị");

        // Xác minh nội dung chính xác của thông báo
        String actualMessage = successMessage.getText();
        Assert.assertEquals(actualMessage, "Signed in successfully.", "Nội dung thông báo không khớp");
    }

    /*@Test
    public void TC_05_CheckSuccessNotification() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Signed in successfully.')]")));

        // Xác minh thông báo
        Assert.assertTrue(successMessage.isDisplayed());
    }
*/



    /*
    @Test
    public void TC_01_Url() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
    }

    @Test
    public void TC_02_Logo() {
        Assert.assertTrue(driver.findElement(By.cssSelector("img.fb_logo")).isDisplayed());
    }

    @Test
    public void TC_03_Form() {
        Assert.assertTrue(driver.findElement(By.xpath("//form[@data-testid='royal_login_form']")).isDisplayed());
    }
*/
    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}