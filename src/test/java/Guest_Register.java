import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Guest_Register {
    WebDriver driver;
    @BeforeAll
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headed");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
    }
    @DisplayName("Registration Successful")
    @Test
    public void register() throws InterruptedException {
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName  = faker.name().lastName();
        String email     = faker.internet().emailAddress();
        String password  = faker.internet().password()+"#*1$";
        int month        = faker.number().numberBetween(1, 13);
        int date         = faker.number().numberBetween(1,30);
        int year         = faker.number().numberBetween(1900,2024);
        int gender       = faker.number().numberBetween(0,2);
        String spanText  = String.valueOf(date);
        String inputYear = String.valueOf(year) ;
        String phone     = faker.phoneNumber().cellPhone();


        List<WebElement> input_text_fields = driver.findElements(By.className("input-text"));
        input_text_fields.get(0).sendKeys(firstName);
        input_text_fields.get(1).sendKeys(email);
        input_text_fields.get(2).sendKeys(password);
        input_text_fields.get(3).sendKeys(lastName);
        input_text_fields.get(5).sendKeys("Bangladeshi");
        List<WebElement> inputs =driver.findElements(By.cssSelector("[type='text']"));
        inputs.get(2).click();
        List<WebElement> monthDropdowns = driver.findElements(By.className("flatpickr-monthDropdown-months"));
        monthDropdowns.get(0).click();
        Select select = new Select(monthDropdowns.get(0));
        select.selectByIndex(month-1);
        List<WebElement> numberInputs = driver.findElements(By.className("numInput"));
        numberInputs.get(0).clear();
        numberInputs.get(0).sendKeys(inputYear);
        WebElement spanElement = driver.findElement(By.xpath("//span[text()='" + spanText + "']"));
        spanElement.click();
        List<WebElement> radioBtns = driver.findElements(By.className("input-radio"));
        radioBtns.get(gender).click();
        //country_1665629257
        WebElement countryDropdowns = driver.findElement(By.id("country_1665629257"));
        countryDropdowns.click();
        Select select1 = new Select(countryDropdowns);
        select1.selectByValue("BD");
        driver.findElements(By.id("phone_1665627880")).get(1).sendKeys(phone);
        scroll();
        driver.findElement(By.id("privacy_policy_1665633140")).click();
        WebElement submitSpan = driver.findElement(By.className("btn"));
        submitSpan.click();
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement ul = driver.findElement(By.xpath("//ul[text() = \"User successfully registered.\"]"));
        wait.until(
                ExpectedConditions.visibilityOf(ul));
        String successful_msg = ul.getText();
        Assertions.assertTrue(successful_msg.contains("User successfully registered."));

    }
    public void scroll(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    @AfterAll
    public void closeBrowser(){
//        driver.quit();
    }
}
