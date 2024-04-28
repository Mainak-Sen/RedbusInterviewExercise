package com.redbus.test;

import org.mockito.Mockito;
import pageObjects.BusesPage;
import pageObjects.CustomerDetails;
import pageObjects.LandingPage;
import pageObjects.PaymentPage;

import java.util.List;

public class BookingFlowHelper extends BaseTest {

    // Method to simulate the booking process
    protected boolean performBookingProcess() {
        // Perform booking process, including clicking on payment button
        paymentPage.clickPayThroughCreditCard();

        // Simulate payment using paymentServiceMock
        boolean paymentStatus = paymentServiceMock.makePayment(Mockito.anyDouble());

        // Simulate waiting for payment response
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return paymentStatus;
    }

    public LandingPage signIn(String mobileNumber) {
        loginPage.clickLoginFromProfile();
        loginPage.enterPhoneNumber(mobileNumber);
        landingPage = loginPage.clickSignInButton();
        return landingPage;
    }

    public BusesPage enterDetailsAndSearchBuses(String date, String source, String destination) {
        landingPage.enterDate(date);
        landingPage.enterSource(source);
        landingPage.enterDestination(destination);
        busesPage = landingPage.clickSearchBusesButton();
        return busesPage;
    }

    public CustomerDetails enterPointOperatorAndSeatsDetails(String operator, String boardingPoint, String droppingPoint, String seatsCount, List<String> selectedSeats){
        busesPage.selectBusOperator(operator);
        busesPage.selectBoardingPoint(boardingPoint);
        busesPage.selectDroppingPoint(droppingPoint);
        busesPage.enterQuantity(Integer.parseInt(seatsCount));

        seatDetails.selectSeatNumbers(selectedSeats);
        customerDetails = seatDetails.clickProceed();
        return customerDetails;
    }

    public PaymentPage enterCustomerDetailsAndMakePayment(String customerName, String email, String mobile){
        customerDetails.enterCustomerName(customerName);
        customerDetails.enterCustomerEmailId(email);
        customerDetails.enterCustomerMobile(mobile);
        paymentPage = customerDetails.clickMakePayment();
        return paymentPage;
    }


}
