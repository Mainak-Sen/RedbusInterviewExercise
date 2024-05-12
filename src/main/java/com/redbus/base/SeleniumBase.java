package com.redbus.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


//import org.apache.logging.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumBase implements Browser, Element {


    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    //protected static RemoteWebDriver driver;
    protected static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected static String PageTitle;
    protected Actions builder;
    protected static Logger log = LogManager.getLogger(SeleniumBase.class.getName());

    public void get_page_title() {
        PageTitle = getDriver().getTitle();
    }

    public static RemoteWebDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    public void scroll_into_view(WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ele);
        log.debug("Successfully scrolled into view to the max-price web-element : " + ele);
    }

    public void scrollQuarter() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)");
    }

    public void scrollHalf() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
    }

    public void scrollThreefourth() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(500,750)");
    }

    public void highlight_element(WebElement ele) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px solid blue'", ele);
        log.info("Successfully highlighted max  price element in blue: " + ele);
    }

    public void scroll_until_bottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(750, document.body.scrollHeight)");
    }

    public void click(WebElement ele) {
        try {
            getWait().until(ExpectedConditions.visibilityOf(ele));
            if (ele.isDisplayed()) {
                getWait().until(ExpectedConditions.elementToBeClickable(ele));

                if (ele.isEnabled()) {
                    ele.click();
                    log.debug("Successfully clicked element: " + ele);
                } else {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
                    log.debug("Successfully clicked element: " + ele);
                }
            }
            //System.out.println("The element: "+ele+" has been clicked  successfully ");
            log.debug("The element: " + ele + " has been clicked  successfully ");
        } catch (TimeoutException e) {
            //System.err.println("Timeout Exception occured,retrying to click element: "+ele);
            log.debug("Timeout Exception occured,retrying to click element: " + ele);
            builder.moveToElement(ele).pause(2000).click().perform();
            // System.out.println("Successfully clicked after retrying");
            log.debug("Successfully clicked after retrying");
        } catch (StaleElementReferenceException e) {
            //System.err.println("The elment: "+ele+" to be clicked appears to be stale,refer screesnhot attached ");
            log.error("The elment: " + ele + " to be clicked appears to be stale,refer screesnhot attached ");
        } catch (Exception e) {
            //System.err.println("Exception occured while clicking element: "+ele+"\n"+e.getMessage());
            log.error("Exception occured while clicking element: " + ele + "\n" + e.getMessage());
        }
    }

    public void append(WebElement ele, String data) {
        // TODO Auto-generated method stub
        try {
            if (data.equals(null)) {
                throw new IllegalArgumentException("Keys to send cannot be null");
            } else {
                ele.sendKeys(data);
                System.err.println("The data: " + data + " is successfully entered in the webElement: " + ele);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException occured with data: " + data + "\n" + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("ElementNotInteractableException occured with element: " + ele + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occured while entering data:" + data + "into element: " + ele + "\n" + e.getMessage());
        }
    }

    public void clear(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            ele.clear();
            System.out.println("The element " + ele + " content cleared successfully");
        } catch (InvalidElementStateException e) {
            System.err.println("InvalidElementStateException occured while trying to clear " + ele + " content");
        } catch (Exception e) {
            System.err.println("Exception occured while clearing element " + ele + " " + "\n" + e.getMessage());
        }


    }

    public void clearAndType(WebElement ele, String data) {
        try {
            ele.clear();
            System.out.println("Element: " + ele + " " + "content cleared successfully");
            ele.sendKeys(data);
            if (data.equals(null)) {
                throw new IllegalArgumentException("Keys to send cannot be null");
            } else {
                ele.sendKeys(data);
                System.err.println("The data: " + data + " is successfully entered in the webElement: " + ele);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException occured with data: " + data + "\n" + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("ElementNotInteractableException occured with element: " + ele + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occured while clearing and entering data:" + data + "into element: " + ele + "\n" + e.getMessage());
        } finally {
            File src = getDriver().getScreenshotAs(OutputType.FILE);
            try {
                FileHandler.copy(src, new File("./Screenshots/clearndType.png"));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.err.println("I/O Exception occured whie trying to copy screenshot file: " + "\n" + e1.getMessage());
            }
        }
    }

    public String getElementText(WebElement ele) {
        // TODO Auto-generated method stub
        String text = "";
        try {
            text = ele.getText();
            //System.out.println("Element "+ele+" text: "+text+"extracted successfully" );
            log.debug("Element " + ele + " text: " + text + "extracted successfully");
        } catch (Exception e) {
            //System.err.println("Exception occured while extracting text from element: "+ele+"\n"+e.getMessage());
            log.error("Exception occured while extracting text from element: " + ele + "\n" + e.getMessage());
        }
        return text;
    }

    public String getBackgroundColor(WebElement ele) {
        // TODO Auto-generated method stub
        String CssValue = null;
        try {
            CssValue = ele.getCssValue("background-color");
            System.out.println("The background color of the element " + ele + " is" + CssValue);
        } catch (Exception e) {
            System.err.println("CSS value couldn't be extracted for element " + ele + "\n" + e.getMessage());
        }

        return CssValue;
    }

    public String getTypedText(WebElement ele) {
        // TODO Auto-generated method stub
        String propertyValue = null;
        try {
            propertyValue = ele.getAttribute("value");
            System.out.println("Value of the attribute extracted is: " + propertyValue);
        } catch (Exception e) {
            System.err.println("Typed text attribute couldnt be found for element: " + ele + "\n" + e.getMessage());
        }
        return propertyValue;
    }

    public void selectDropDownUsingText(WebElement ele, String value) {
        // TODO Auto-generated method stub
        try {
            Select s = new Select(ele);
            s.selectByVisibleText(value);
            System.out.println("Successfully selected the dropdown with visible text: " + value + "for dropdown element " + ele);
        } catch (Exception e) {
            System.err.println("Not able to select dropdown with visibe-text " + value + "\n" + e.getMessage());
        }

    }

    public void selectDropDownUsingIndex(WebElement ele, int index) {
        // TODO Auto-generated method stub
        try {
            Select s = new Select(ele);
            s.selectByIndex(index);
            System.out.println("Successfully selected the dropdown with index: " + index + "for dropdown element " + ele);
        } catch (Exception e) {
            System.err.println("Not able to select dropdown with index " + index + "\n" + e.getMessage());
        }

    }

    public void selectDropDownUsingValue(WebElement ele, String value) {
        // TODO Auto-generated method stub
        try {
            Select s = new Select(ele);
            s.selectByValue(value);
            System.out.println("Successfully selected the dropdown with value: " + value + "for dropdown element " + ele);
        } catch (Exception e) {
            System.err.println("Not able to select dropdown with value: " + value + "\n" + e.getMessage());
        }

    }

    public boolean verifyExactText(WebElement ele, String expectedText) {
        // TODO Auto-generated method stub
        try {
            String actual_text = ele.getText();
            if (actual_text.equals(expectedText)) {
                System.out.println(expectedText + " matched with " + actual_text);
                return true;
            } else {
                System.out.println("Expected: " + expectedText + "but found" + actual_text);
            }
        } catch (Exception e) {
            System.err.println("Verifying element with text: " + expectedText + "failed" + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifyPartialText(WebElement ele, String expectedText) {
        // TODO Auto-generated method stub
        try {
            String actual_text = ele.getText();
            if (actual_text.contains(expectedText)) {
                System.out.println(expectedText + " found in " + actual_text);
                return true;
            } else {
                System.out.println("Expected: " + expectedText + "not found in" + actual_text);
            }
        } catch (Exception e) {
            System.err.println("Verifying element with partial text: " + expectedText + "failed" + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
        // TODO Auto-generated method stub
        try {
            String ActualValue = ele.getAttribute(attribute);
            if (ActualValue.equals(value)) {
                System.out.println(attribute + " Expected value: " + value + " matched with " + " actual value " + ActualValue);
                return true;
            } else {
                System.out.println(attribute + " Expected value: " + value + " did not match with " + " actual value " + ActualValue);
            }
        } catch (Exception e) {
            System.err.println("Exception occured while verifying exact attibute text of attribute:" + attribute + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifyPartialAttribute(WebElement ele, String attribute, String value) {
        // TODO Auto-generated method stub
        try {
            String ActualValue = ele.getAttribute(attribute);
            if (ActualValue.contains(value)) {
                System.out.println(attribute + " Expected value: " + value + " found in " + " actual value " + ActualValue);
                return true;
            } else {
                System.out.println(attribute + " Expected value: " + value + " not found in  " + " actual value " + ActualValue);
            }
        } catch (Exception e) {
            System.err.println("Exception occured while verifying partial attibute text of attribute:" + attribute + "\n" + e.getMessage());
        }
        return false;

    }

    public boolean verifyDisplayed(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            if (ele.isDisplayed()) {
                //System.out.println("Element "+ele+" is displayed in DOM");
                log.debug("Element " + ele + " is displayed in DOM");
                return true;
            } else {
                //System.err.println("Element "+ele+" is not displayed in DOM");
                log.error("Element " + ele + " is not displayed in DOM");
            }
        } catch (Exception e) {
            //System.err.println("Exception occured while checking visibility of element "+ele+"\n"+e.getMessage());
            log.error("Exception occured while checking visibility of element " + ele + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifyDisappeared(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            boolean is_disappeared = getWait().until(ExpectedConditions.invisibilityOf(ele));
            System.out.println("waiting for element " + ele + " to disappear");
            return is_disappeared;
        } catch (TimeoutException e) {
            System.err.println("Element " + ele + " did not disappear" + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Element " + ele + " did not disappear" + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifyEnabled(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            if (ele.isEnabled()) {
                System.out.println("Element " + ele + " is enabled");
                return true;
            } else {
                System.out.println("Element " + ele + " is not enabled");
            }
        } catch (Exception e) {
            System.err.println("Exception occured while checking if element " + ele + " is enabled" + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean verifySelected(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            if (ele.isSelected()) {
                System.out.println("Element " + ele + " is selected");
                return true;
            } else {
                System.out.println("Element " + ele + " is not selected");
            }
        } catch (Exception e) {
            System.err.println("Exception occured while checking if element " + ele + " is selected" + "\n" + e.getMessage());
        }
        return false;
    }

    public void startApp(String url) {
        try {
            //System.setProperty("webgetDriver().chrome.driver","./Drivers/chromegetDriver().exe");
            if (getDriver() == null) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.addArguments("--remote-allow-origins=*");
                ChromeDriver chromeDriver = new ChromeDriver(options);
                driver.set(chromeDriver);
                wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
                builder = new Actions(getDriver());
                getDriver().manage().window().maximize();
                getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                getDriver().get(url);
                //System.out.println("The browser has been launched");
                log.debug("The browser has been launched");
            }
        } catch (Exception e) {
            //System.err.println("Browser could not launch "+e.getMessage());
            log.error("Browser could not launch " + e.getMessage());
        }
    }

    public void startApp(String browser, String url) {
        // TODO Auto-generated method stub
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                //System.setProperty("webgetDriver().chrome.driver", "./Drivers/drivers/chromegetDriver().exe");
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
            } else if (browser.equalsIgnoreCase("firefox")) {
                //System.setProperty("webgetDriver().gecko.driver", "./Drivers/drivers/geckogetDriver().exe");
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
            } else if (browser.equalsIgnoreCase("IE")) {
                //System.setProperty("webgetDriver().ie.driver", "./Drivers/drivers/IEDriverServer.exe");
                driver.set(new InternetExplorerDriver());
            }
            wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
            //js=(JavascriptExecutor)driver;
            builder = new Actions(getDriver());
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            getDriver().get(url);
            System.out.println("The browser has been launched");
        } catch (Exception e) {
            System.err.println("Browser could not launch " + e.getMessage());
        }

    }

    public WebElement findElementBy(Locators locator_type, String value) {
        try {
            switch (locator_type) {
                case ID:
                    return getDriver().findElement(By.id(value));
                case CLASS_NAME:
                    return getDriver().findElement(By.className(value));
                case XPATH:
                    return getDriver().findElement(By.xpath(value));
                case NAME:
                    return getDriver().findElement(By.name(value));
                case CSS_SELECTOR:
                    return getDriver().findElement(By.cssSelector(value));
                case LINK_TEXT:
                    return getDriver().findElement(By.linkText(value));
                case PARTIAL_LINK_TEXT:
                    return getDriver().findElement(By.partialLinkText(value));
                case TAG_NAME:
                    return getDriver().findElement(By.tagName(value));
                default:
                    //System.err.println("Locator type is wrong");
                    log.error("Locator type is wrong");
                    break;
            }
        } catch (NoSuchElementException e) {
            //System.err.println("Element not found");
            log.error("Element not found " + e.getMessage());
        } catch (Exception e) {
            //System.err.println("Element with locator: "+locator_type+"not found with value: "+value+"\n"+e.getMessage());
            log.error("Element with locator: " + locator_type + "not found with value: " + value + "\n" + e.getMessage());
        }
        return null;

    }

    public List<WebElement> findElementsBy(Locators locator_type, String value) {
        // TODO Auto-generated method stub
        try {

            switch (locator_type) {

                case ID:
                    return getDriver().findElements(By.id(value));
                case CLASS_NAME:
                    return getDriver().findElements(By.className(value));
                case XPATH:
                    return getDriver().findElements(By.xpath(value));
                case NAME:
                    return getDriver().findElements(By.name(value));
                case CSS_SELECTOR:
                    return getDriver().findElements(By.cssSelector(value));
                case LINK_TEXT:
                    return getDriver().findElements(By.linkText(value));
                case PARTIAL_LINK_TEXT:
                    return getDriver().findElements(By.partialLinkText(value));
                case TAG_NAME:
                    return getDriver().findElements(By.tagName(value));
                default:
                    //System.err.println("Locator type is wrong");
                    log.error("Locator type is wrong");
                    break;
            }
        } catch (Exception e) {
            //System.err.println("Elements with locator: "+locator_type+"not found with value: "+value+"\n"+e.getMessage());
            log.error("Elements with locator: " + locator_type + "not found with value: " + value + "\n" + e.getMessage());
        }
        return null;
    }

    public void switchToAlert() {
        // TODO Auto-generated method stub
        try {
            getDriver().switchTo().alert();
            System.out.println("Switched to Alert window");
        } catch (NoAlertPresentException e) {
            System.err.println("No such alert is found");
        } catch (Exception e) {
            System.err.println("Exception occured:" + e.getMessage());
        }


    }

    public void acceptAlert() {
        // TODO Auto-generated method stub
        try {
            getDriver().switchTo().alert().accept();
            System.out.println("Switched to Alert window and accepted the same");
        } catch (NoAlertPresentException e) {
            System.err.println("No such alert is found");
        } catch (Exception e) {
            System.err.println("Exception occured:" + e.getMessage());
        }

    }

    public void dismissAlert() {
        // TODO Auto-generated method stub
        try {
            getDriver().switchTo().alert().dismiss();
            System.out.println("Switched to Alert window and dismissed the same");
        } catch (NoAlertPresentException e) {
            System.err.println("No such alert is found");
        } catch (Exception e) {
            System.err.println("Exception occured:" + e.getMessage());
        }


    }

    public String getAlertText() {
        // TODO Auto-generated method stub
        String text = "";
        try {
            text = getDriver().switchTo().alert().getText();
            System.out.println("The text present in the alert is: " + text);
        } catch (NoAlertPresentException e) {
            System.err.println("No such alert is found");
        } catch (Exception e) {
            System.err.println("Exception occured:" + e.getMessage());
        }
        return text;
    }

    public void typeAlert(String data) {
        // TODO Auto-generated method stub
        try {
            getDriver().switchTo().alert().sendKeys(data);
            ;
            System.out.println("Successfully typed the data in the alert window");
        } catch (NoAlertPresentException e) {
            System.err.println("No such alert is found");
        } catch (Exception e) {
            System.err.println("Exception occured:" + e.getMessage());
        }

    }

    public void switchToWindow(int index) {
        // TODO Auto-generated method stub
        try {
            Set<String> window_handles = getDriver().getWindowHandles();
            List<String> window_handles_list = new ArrayList<String>();
            window_handles_list.addAll(window_handles);
            if (index > 0) {
                getWait().until(ExpectedConditions.numberOfWindowsToBe(index + 1));
            }
            String title = getDriver().switchTo().window(window_handles_list.get(index)).getTitle();
            System.out.println("Successfully switched to the desired window index: " + index);
            System.out.println("The title of the current window is: " + title);
        } catch (NoSuchWindowException e) {
            System.err.println("The window with index: " + index + " was not found");
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }

    }

    public void switchToWindow_withExacttitle(String exact_title) {
        // TODO Auto-generated method stub
        try {
            Set<String> window_handles = getDriver().getWindowHandles();
            String parent_window = getDriver().getWindowHandle();
            for (String window : window_handles) {
                getDriver().switchTo().window(window);
                if (getDriver().switchTo().window(window).getTitle().equals(exact_title)) {
                    break;
                }
            }
            System.out.println("Successfully switched to window with title: " + exact_title);
        } catch (NoSuchWindowException e) {
            System.err.println("The window with title: " + exact_title + " was not found");
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }

    }


    public void switchToWindow_withPartialtitle(String partial_title) {
        // TODO Auto-generated method stub
        try {
            Set<String> window_handles = getDriver().getWindowHandles();
            String parent_window = getDriver().getWindowHandle();
            for (String window : window_handles) {
                if (getDriver().switchTo().window(window).getTitle().contains(partial_title)) {
                    break;
                } else {
                    getDriver().switchTo().window(parent_window);
                }
            }
            System.out.println("Successfully switched to window with the partial title: " + partial_title);
        } catch (NoSuchWindowException e) {
            System.err.println("The window with partial title: " + partial_title + " was not found");
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }

    }

    public void switchToFrame(int index) {
        try {
            Thread.sleep(5000);
            //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
            getDriver().switchTo().frame(index);
            System.out.println("Successfully switched to frame with index: " + index);
        } catch (NoSuchFrameException e) {
            System.err.println("No such frame found with frame index: " + index);
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }

    }

    public void switchToFrame(WebElement ele) {
        // TODO Auto-generated method stub
        try {
            //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(ele));
            getDriver().switchTo().frame(ele);
            System.out.println("Successfully switched to frame with WebElement: " + ele);
        } catch (NoSuchFrameException e) {
            System.err.println("No such frame found with the given WebElement: " + ele);
        } catch (StaleElementReferenceException e) {
            System.err.println("The element " + ele + " with which you are trying to locate frame appears to be stale");
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }


    }

    public void switchToFrame(String idOrName) {
        // TODO Auto-generated method stub
        try {
            //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
            getDriver().switchTo().frame(idOrName);
            System.out.println("Successfully switched to frame with id/name: " + idOrName);
        } catch (NoSuchFrameException e) {
            System.err.println("No such frame found with id/name : " + idOrName);
        } catch (Exception e) {
            System.err.println("Exception occured: " + e.getMessage());
        }

    }

    public void defaultContent() {
        // TODO Auto-generated method stub
        try {
            getDriver().switchTo().defaultContent();
            System.out.println("Successfully switched to the first or the default window");
        } catch (Exception e) {
            System.err.println("No such window found " + e.getMessage());
        }
    }

    public boolean verifyUrl(String url) {
        // TODO Auto-generated method stub
        if (getDriver().getCurrentUrl().equals(url)) {
            //System.out.println("The given url: "+url+" matched successfully");
            log.debug("The given url: " + url + " matched successfully");
            return true;
        } else {
            //System.err.println("Failed to match with the given url: "+url);
            log.error("Failed to match with the given url: " + url);
            return false;
        }

    }

    public boolean verifyTitle(String title) {
        // TODO Auto-generated method stub
        if (getDriver().getTitle().equals(title)) {
            System.out.println("The given title: " + title + " matched successfully");
            return true;
        } else {
            System.err.println("Failed to match with the given title: " + title);
            return false;
        }
    }

    public void close() {
        // TODO Auto-generated method stub
        try {
            getDriver().close();
            System.out.println("Successfully closed the active browser");
        } catch (Exception e) {
            System.err.println("Browser cannot be closed: " + e.getMessage());
        }

    }

    public void quit() {
        // TODO Auto-generated method stub
        try {
            getDriver().quit();
            //System.out.println("Successfully closed all the browsers");
            log.debug("Successfully closed all the browsers");
        } catch (Exception e) {
            //System.err.println("Browsers cannot be closed: "+ e.getMessage());
            log.error("Browsers cannot be closed: " + e.getMessage());
        }

    }


}
