package com.redbus.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.redbus.base.SeleniumBase;
import com.redbus.mocks.PaymentService;
import com.redbus.reporting.ExtentManager;
import org.mockito.Mockito;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import pageObjects.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseTest extends SeleniumBase {

    protected static LandingPage landingPage;
    protected static LoginPage loginPage;
    protected static BusesPage busesPage;
    protected static SeatDetails seatDetails;
    protected static CustomerDetails customerDetails;
    protected static PaymentPage paymentPage;
    protected static PaymentService paymentServiceMock;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;
    // Define other PageObject classes here

    static {
        landingPage = new LandingPage();
        seatDetails = new SeatDetails();
        // Instantiate other PageObject classes as needed
    }


    @BeforeClass
    public void setup() {
        //String appUrl = System.getProperty("applicationUrl");
        paymentServiceMock = Mockito.mock(PaymentService.class);
        extent = ExtentManager.getInstance();
        extentTest = extent.createTest("Redbus Test");
        String appUrl = "https://www.redbus.in/";
        String browser = System.getProperty("browser");
        if (browser != null) {
            startApp(browser, appUrl);
        } else {
            startApp(appUrl);
        }
    }

    @AfterClass
    public void tearDown() {
        quit();
        extent.flush();
    }

    @DataProvider(name = "bookingData")
    public Object[][] getBookingData() throws IOException {
        // Read test data from CSV file
        String csvFile = "testData/testData.csv";
        String line;
        List<Object[]> testDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();

            // Read each line and split into array
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Assuming each line contains 12 elements
                Object[] testData = new Object[12];

                // Store data in the array
                List<String> selectedSeats = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    if (data[i].startsWith("seat-")) {
                        String[] hyphenDelimited = data[i].split("-");
                        for (int j = 1; j < hyphenDelimited.length; j++) {
                            selectedSeats.add(hyphenDelimited[j]);
                        }
                        testData[i] = selectedSeats;
                    } else {
                        testData[i] = data[i].trim();
                    }
                }
                testDataList.add(testData);
            }
        }

        // Convert list to array
        Object[][] testData = new Object[testDataList.size()][];
        testDataList.toArray(testData);

        return testData;
    }
}
