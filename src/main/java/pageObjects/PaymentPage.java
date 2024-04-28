package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;

public class PaymentPage extends SeleniumBase {

    private String payThroughCreditCardId = "credit-card-payment";
    private String payThroughUpi = "upi-payment";
    private String paymentSuccessToast = "successToast";
    private String paymentFailureToast = "failureToast";

    public void clickPayThroughCreditCard() {
        click(findElementBy(Locators.valueOf("ID"), payThroughCreditCardId));
    }

    public void clickPayThroughUPI() {
        click(findElementBy(Locators.valueOf("ID"), payThroughUpi));
    }

    public String getPaymentToast(boolean paymentStatus) {
        String text =  paymentStatus ? getElementText(findElementBy(Locators.valueOf("ID"), paymentSuccessToast)): getElementText(findElementBy(Locators.valueOf("ID"), paymentFailureToast));
        return text;
    }
}
