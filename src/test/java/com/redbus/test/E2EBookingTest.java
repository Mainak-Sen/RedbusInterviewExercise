package com.redbus.test;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class E2EBookingTest extends BookingFlowHelper {


    @Test(dataProvider = "bookingData")
    public void bookTicketTestPaymentSuccess(String source, String destination, String date, String operator,
                                             String boardingPoint, String droppingPoint, String seatsCount,
                                             List<String> selectedSeats, String customerName, String email, String mobile) {

        extentTest.info("Booking ticket payment success test started");
        //sign in
        signIn(mobile);
        //operations on landing page
        enterDetailsAndSearchBuses(date, source, destination);
        //operations on buses page
        enterPointOperatorAndSeatsDetails(operator, boardingPoint, droppingPoint, seatsCount, selectedSeats);
        // enter customer details
        enterCustomerDetailsAndMakePayment(customerName, email, mobile);


        // Mock payment service for success and failure
        Mockito.when(paymentServiceMock.makePayment(Mockito.anyDouble())).thenReturn(true); // Success
        // Perform payment and booking process
        boolean paymentStatus = performBookingProcess();

        // Assert toast message based on payment status
        // Verify successful toast message
        String successToast = paymentPage.getPaymentToast(paymentStatus);
        Assert.assertEquals(successToast, "Payment Successful", "Toast message for successful payment is incorrect");

        extentTest.pass("Booking Ticket test with successful payment passed");
    }

    @Test(dataProvider = "bookingData")
    public void bookTicketTestPaymentFailure(String source, String destination, String date, String operator,
                                             String boardingPoint, String droppingPoint, String seatsCount,
                                             List<String> selectedSeats, String customerName, String email, String mobile) {

        extentTest.info("Booking ticket payment failure test started");
        //sign in
        signIn(mobile);
        //operations on landing page
        enterDetailsAndSearchBuses(date, source, destination);
        //operations on buses page
        enterPointOperatorAndSeatsDetails(operator, boardingPoint, droppingPoint, seatsCount, selectedSeats);
        // enter customer details
        enterCustomerDetailsAndMakePayment(customerName, email, mobile);


        Mockito.when(paymentServiceMock.makePayment(Mockito.anyDouble())).thenReturn(false); // Failure
        // Perform payment and booking process
        boolean paymentStatus = performBookingProcess();

        // Assert toast message based on payment status
        // Verify successful toast message
        String successToast = paymentPage.getPaymentToast(paymentStatus);
        Assert.assertEquals(successToast, "Payment Successful", "Toast message for successful payment is incorrect");

        extentTest.pass("Booking Ticket test with failure payment passed");
    }
}


