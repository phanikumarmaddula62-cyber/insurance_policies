

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HealthModule {


    public static void main(String[] args) throws InterruptedException {
        // Initialize Driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // 1. Execute Car Check Flow
            runCarCheck(driver);
            System.out.println("--- Car Check Completed ---\n");
            driver.get("https://www.coverfox.com/");

            // 2. Execute Health Menu Flow
            runHealthMenu(driver);
            System.out.println("--- Health Menu Completed ---\n");
            driver.get("https://www.coverfox.com/");

            // 3. Execute Health Sort Flow
            runHealthSort(driver);
            System.out.println("--- Health Sort Completed ---\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("All Executions Finished. Closing Browser.");
            driver.quit();
        }
    }

    // --- ORIGINAL CAR CHECK CODE ---
    public static void runCarCheck(WebDriver driver) throws InterruptedException {
        driver.get("https://www.coverfox.com/");
        Thread.sleep(5000);

        By car_xpath = By.xpath("//*[@id='content']/div/ul/li[2]");
        By boughtNewCarXpath = By.xpath("(//div/span[@class='dls-link'])[2]");
        By carVariant = By.xpath("(//div[@class='top-models__item '])[2]");
        By brandXpath = By.xpath("//div/img[@src='https://assets.coverfox.com/static/img/car-product/make-logos/ford.png']");
        By fuelType = By.xpath("(//div/ul/li[@class=' '])[1]");
        By variantType = By.xpath("//div[@class='variants-list']/div[1]");
        By carRegSearchBox = By.xpath("//input[@id='SearchBox']");
        By saveAndcontinuebtn = By.xpath("//button[@title='Save & Continue']");
        By enterMobileNoSearch = By.xpath("//input[@type='tel']");
        By ClickOkNoBtn = By.xpath("//button[@title='View Quotes']");
        By errorMsg = By.xpath("//div[@class='error-label ']");

        driver.findElement(car_xpath).click();
        Thread.sleep(3000);
        driver.findElement(boughtNewCarXpath).click();
        Thread.sleep(3000);
        driver.findElement(brandXpath).click();
        Thread.sleep(3000);
        driver.findElement(carVariant).click();
        Thread.sleep(3000);
        driver.findElement(fuelType).click();
        Thread.sleep(3000);
        driver.findElement(variantType).click();
        Thread.sleep(3000);
        driver.findElement(carRegSearchBox).sendKeys("TN-14  Chennai Sholinganallur");
        Thread.sleep(2000);
        driver.findElement(carRegSearchBox).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(saveAndcontinuebtn).click();
        Thread.sleep(3000);
        driver.findElement(enterMobileNoSearch).sendKeys("555555555");
        Thread.sleep(2000);
        driver.findElement(ClickOkNoBtn).click();
        Thread.sleep(3000);

        WebElement err = driver.findElement(errorMsg);
        System.out.println("ERROR MESSAGE: " + err.getText());
    }

    // --- ORIGINAL HEALTH MENU CODE ---
    public static void runHealthMenu(WebDriver driver) throws InterruptedException {
        driver.get("https://www.coverfox.com/");
        Thread.sleep(5000);
        Actions action = new Actions(driver);

        WebElement retail = driver.findElement(By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]/span[1]"));
        action.moveToElement(retail).perform();
        Thread.sleep(3000);

        WebElement healthMenu = driver.findElement(By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]/span[2]/following-sibling::ul/li[3]"));
        action.moveToElement(healthMenu).perform();
        Thread.sleep(3000);

        List<WebElement> items = driver.findElements(By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]//ul[1]/li[3]//ul/li/a"));

        int count = items.size();
        System.out.println("Total items found: " + count);
        int printed = 0;

        for (WebElement e : items) {
            String text = e.getText().trim();
            System.out.println(text);
            if (!text.isEmpty()) printed++;
        }

        if (printed == count) {
            System.out.println("All items printed successfully.");
        } else {
            System.out.println("Some items were NOT printed.");
        }
    }

    // --- ORIGINAL HEALTH SORT CODE ---
    public static void runHealthSort(WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.coverfox.com/");
        Thread.sleep(4000);

        driver.findElement(By.xpath("//button[contains(text(),'Get Started')]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='ms-title' and text()='Wife']")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//div[@class='next-btn']")).click();
        Thread.sleep(3000);

        Select youAge = new Select(driver.findElement(By.xpath("//select[@id='Age-You']")));
        youAge.selectByVisibleText(" 22 years ");
        Thread.sleep(1000);

        Select wifeAge = new Select(driver.findElement(By.xpath("//select[@id='Age-Spouse']")));
        wifeAge.selectByVisibleText(" 21 years ");
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='next-btn']"))).click();
        Thread.sleep(2000);

        String pinCode = "600001";
        driver.findElement(By.xpath("//input[@placeholder='6 Digit Pincode']")).sendKeys(pinCode);
        Thread.sleep(1500);

        String firstDigit = String.valueOf(6 + (int) (Math.random() * 4));
        long remainingNine = (long) (Math.random() * 1_000_000_000L);
        String randomReach = firstDigit + String.format("%09d", remainingNine);

        driver.findElement(By.xpath("//input[@placeholder='Your mobile number']")).sendKeys(randomReach);
        Thread.sleep(1500);

        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Continue')]")));
        js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        Thread.sleep(1000);
        continueBtn.click();
        Thread.sleep(2000);

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
        } catch (Exception e) {}

        By sumASelector = By.xpath("//div[contains(@class, 'sumA')]//div[contains(@class, 'fc-filter')]");
        WebElement sumAHeader = wait.until(ExpectedConditions.presenceOfElementLocated(sumASelector));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sumAHeader);
        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", sumAHeader);

        WebElement band_1to3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'radio-ui')][span[normalize-space()='1L to 3L']]")));
        band_1to3.click();
        Thread.sleep(2000);

        Select sort = new Select(driver.findElement(By.xpath("//select")));
        sort.selectByVisibleText(" Premium (low-high) ");
        Thread.sleep(5000);

        System.out.println("\n--- TOP 3 POLICIES ---");
        List<WebElement> allPolicies = driver.findElements(By.className("plan-card-container"));

        int printed = 0;
        for (WebElement policy : allPolicies) {
            if (printed >= 3) break;
            try {
                String insurer = policy.findElement(By.xpath(".//img[@class='logo']")).getAttribute("alt");
                String price = policy.findElement(By.className("rupee-val")).getText();
                String premium = policy.findElement(By.xpath(".//span[contains(@class, 'pcc-premium-bold')]//div[@class='rupee-val']")).getText();
                System.out.println((printed + 1) + ". Insurer: " + insurer + " | Price: ₹" + price + " | Premium: ₹" + premium);
                printed++;
            } catch (Exception e) {
                continue;
            }
        }
    }
}
