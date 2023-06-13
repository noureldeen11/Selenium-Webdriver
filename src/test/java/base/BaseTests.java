package base;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import utils.EventReporter;
import utils.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTests {

    private EventFiringWebDriver driver;
    protected HomePage homePage;
    @BeforeClass
    public void setup(){
        System.setProperty("weddriver.chrome.driver","resources/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver(getChromeOptions()));
        driver.register(new EventReporter());
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--start-maximized");
//        driver = new ChromeDriver(options);
        goHome();
        setCookie();
    }

    @BeforeMethod
    public void goHome(){
        driver.get("https://the-internet.herokuapp.com/");
        homePage = new HomePage(driver);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @AfterMethod
    public void recordFailure(ITestResult result){

        if(ITestResult.FAILURE == result.getStatus())
        {
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);

            try{
                Files.move(screenshot, new File("resources/screenshots/test.png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public WindowManager getWindowManager(){
        return new WindowManager(driver);
    }

    private ChromeOptions getChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        //options.setHeadless(true);
        return options;
    }

    private void setCookie(){
        Cookie cookie = new Cookie.Builder("tau", "123")
                .domain("the-internet.herokuapp.com")
                .build();
        driver.manage().addCookie(cookie);
    }
//        WebElement inputsLink = driver.findElement(By.linkText("Inputs"));
//        inputsLink.click();

//        List<WebElement> links = driver.findElements(By.tagName("a"));
//        System.out.println(links.size());

        //1 - Maximize the window
        //driver.manage().window().maximize();

        //2 - Fullscreen mode
        //driver.manage().window().fullscreen();

//        //3 - Specific width (show Chrome iPhoneX emulator)
//        Dimension size = new Dimension(375, 812);
//        driver.manage().window().setSize(size);

//        System.out.println(driver.getTitle());

//        driver.findElement(By.linkText("Shifting Content")).click();
//        Thread.sleep(3000);
//
//        driver.findElement(By.linkText(("Example 1: Menu Element"))).click();
//        Thread.sleep(3000);
//
//        List<WebElement> menuItems = driver.findElements(By.tagName("li"));
//        System.out.println("Number of menu items: " + menuItems.size());

}
