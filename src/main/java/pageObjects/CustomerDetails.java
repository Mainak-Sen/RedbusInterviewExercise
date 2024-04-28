package pageObjects;

import com.redbus.base.Locators;
import com.redbus.base.SeleniumBase;

public class CustomerDetails extends SeleniumBase {

    private String customerNameId = "customer-name";
    private String email = "email-id";
    private String mobile = "mobile-no";
    private String makePayment = "make-payment-id";

    public void enterCustomerName(String customerName) {
        append(findElementBy(Locators.valueOf("ID"), customerNameId), customerName);
    }

    public void enterCustomerEmailId(String emailId) {
        append(findElementBy(Locators.valueOf("ID"), email), emailId);
    }

    public void enterCustomerMobile(String mobileNumber) {
        append(findElementBy(Locators.valueOf("ID"), mobile), mobileNumber);
    }

    public PaymentPage clickMakePayment() {
        click(findElementBy(Locators.valueOf("ID"), makePayment));
        return new PaymentPage();
    }

}
