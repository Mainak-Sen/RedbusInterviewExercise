package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;

public class LoginPage extends SeleniumBase {

    private String signInButtonXpath = "//div[@class='redbus-logo']";
    private String phoneNumberXpath = "//div[@class='phone-number']";
    private String clickLoginFromProfile = "//div[@class='login']";

    public void enterPhoneNumber(String phoneNumber) {
        append(findElementBy(Locators.valueOf("XPATH"), phoneNumberXpath), phoneNumber);
    }

    public LandingPage clickSignInButton() {
        click(findElementBy(Locators.valueOf("XPATH"), signInButtonXpath));
        return new LandingPage();
    }

    public void clickLoginFromProfile() {
        click(findElementBy(Locators.valueOf("XPATH"), signInButtonXpath));
    }

}
