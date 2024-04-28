package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;

public class LandingPage extends SeleniumBase {

    private String sourceInputId = "src";
    private String sourceDropdown = "//li[@data-id='0']";
    private String destinationInputId = "dest";
    private String destinationDropdown = "//li[@data-id='0']";
    private String dateInputId = "dateInput";
    private String searchBusesButtonId = "search_btn";

    public void enterSource(String source) {
        append(findElementBy(Locators.valueOf("ID"), sourceInputId), source);
        click(findElementBy(Locators.valueOf("XPATH"), sourceDropdown));
    }

    public void enterDestination(String destination) {
        append(findElementBy(Locators.valueOf("ID"), destinationInputId), destination);
        click(findElementBy(Locators.valueOf("XPATH"), destinationDropdown));
    }

    public void enterDate(String date) {
        append(findElementBy(Locators.valueOf("ID"), dateInputId), date);
    }

    public BusesPage clickSearchBusesButton() {
        click(findElementBy(Locators.valueOf("ID"), searchBusesButtonId));
        return new BusesPage();
    }

}
