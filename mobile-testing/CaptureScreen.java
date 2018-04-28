package testSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static com.thoughtworks.selenium.SeleneseTestCase.assertNotEquals;
import static org.testng.Assert.assertEquals;

public class CaptureScreen {
    AndroidDriver driver;
    String fileName = "ttgt.apk";
    File app = new File("src/main/resources/" + fileName);
    DesiredCapabilities dc = new DesiredCapabilities();
    String folder_name;
    DateFormat df;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        dc.setCapability("app", app);
        dc.setCapability("platformName", "ANDROID");
        // khai báo deviceName
        dc.setCapability("deviceName", "Real Device");
        dc.setCapability(MobileCapabilityType.UDID, "");
        driver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"), dc);
        driver.setLogLevel(Level.INFO);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void testcemra() {
        folder_name="screenshot";

        driver.findElementByAccessibilityId("videocamCamera").click();

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@content-desc=\'Nam Kỳ Khởi Nghĩa - Điện Biên Phủ 1\']")).click();

        for (int i = 1; i <= 360; ++i) {
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            df=new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

            new File(folder_name).mkdir();

            String file_name=df.format(new Date())+".png";

            try {
                FileUtils.copyFile(f, new File(folder_name + "/" + file_name));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Take [ " + i + " ].");
        }

    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
