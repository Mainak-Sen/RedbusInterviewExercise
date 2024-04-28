package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BusesPage extends SeleniumBase {

    private String busOperatorId = "operator";
    private String boardingPointId = "boardingPoint";
    private String droppingPointtId = "dest";
    private String seatQuanityId = "seats";

    public void selectBusOperator(String busOperator) {
        List<WebElement> elements = findElementsBy(Locators.valueOf("ID"), busOperatorId);
        for (WebElement ele : elements) {
            if (ele.getText().equals(busOperator)) {
                click(ele);
                break;
            }
        }

        throw new RuntimeException("bus operator: " + busOperator + "not found");
    }

    public void selectBoardingPoint(String boardingPoint) {
        List<WebElement> elements = findElementsBy(Locators.valueOf("ID"), boardingPointId);
        for (WebElement ele : elements) {
            if (ele.getText().equals(boardingPoint)) {
                click(ele);
                break;
            }
        }

        throw new RuntimeException("boarding Point: " + boardingPoint + "not found");
    }

    public void selectDroppingPoint(String droppingPoint) {
        List<WebElement> elements = findElementsBy(Locators.valueOf("ID"), droppingPointtId);
        for (WebElement ele : elements) {
            if (ele.getText().equals(droppingPoint)) {
                click(ele);
                break;
            }
        }

        throw new RuntimeException("dropping Point: " + droppingPoint + "not found");
    }

    public void enterQuantity(Integer quantity) {
        append(findElementBy(Locators.valueOf("ID"), seatQuanityId), String.valueOf(quantity));
    }
}
