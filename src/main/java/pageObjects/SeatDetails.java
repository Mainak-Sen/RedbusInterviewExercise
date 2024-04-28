package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeatDetails extends SeleniumBase {

    private String seatNumberId = "seatNumber";
    private String proceed = "proceed-id";

    public void selectSeatNumbers(List<String> seatNumbers) {
        List<WebElement> elements = findElementsBy(Locators.valueOf("ID"), seatNumberId);
        int count = 0;
        for (String seatNumber : seatNumbers) {
            for (WebElement ele : elements) {
                if (ele.getText().equals(seatNumber)) {
                    //select the seat number
                    click(ele);
                    count++;
                }
                if (count == seatNumbers.size() - 1) {
                    break;
                }
            }
        }

        throw new RuntimeException("All seats listed in seat numbers are not found or not empty");
    }

    public CustomerDetails clickProceed() {
        click(findElementBy(Locators.valueOf("ID"), proceed));
        return new CustomerDetails();
    }
}
