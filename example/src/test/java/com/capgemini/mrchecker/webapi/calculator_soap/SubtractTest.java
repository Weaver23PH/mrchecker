package com.capgemini.mrchecker.webapi.calculator_soap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsWebApi;
import com.capgemini.mrchecker.test.core.BaseTest;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.capgemini.mrchecker.test.core.utils.PageFactory;
import com.capgemini.mrchecker.webapi.pages.calculator_soap.SubtractPage;

import io.restassured.response.Response;

@TestsWebApi
public class SubtractTest extends BaseTest {
	private SubtractPage subtractPage;
	
	@BeforeEach
	public void setUpClass() {
		subtractPage = PageFactory.getPageInstance(SubtractPage.class);
	}
	
	@Test
	public void subtractTwoPositiveNumbers() {
		int firstNumberToSubtract = 29;
		int secondNumberToSubtract = 40;
		
		runSubtractingTest(firstNumberToSubtract, secondNumberToSubtract);
	}
	
	@Test
	public void SubtractTwoNegativeNumbers() {
		int firstNumberToSubtract = -29;
		int secondNumberToSubtract = -40;
		
		runSubtractingTest(firstNumberToSubtract, secondNumberToSubtract);
	}
	
	@Test
	public void SubtractTwoMixedNumbers() {
		int firstNumberToSubtract = 29;
		int secondNumberToSubtract = -40;
		
		runSubtractingTest(firstNumberToSubtract, secondNumberToSubtract);
	}
	
	private void runSubtractingTest(int firstNumberToSubtract, int secondNumberToSubtract) {
		BFLogger.logInfo("Step 1 - Setting first number to subtract: " + firstNumberToSubtract);
		subtractPage.setIntA(firstNumberToSubtract);
		
		BFLogger.logInfo("Step 2 - Setting second number to subtract: " + secondNumberToSubtract);
		subtractPage.setIntB(secondNumberToSubtract);
		
		BFLogger.logInfo("Step 3 - Sending SOAP query to subtract two numbers. Endpoint: " + subtractPage.getEndpoint());
		Response response = subtractPage.sendPOST();
		
		BFLogger.logInfo("Step 4 - Validating the response status code");
		assertThat(response.statusCode(), is(200));
		
		BFLogger.logInfo("Step 5 - Validating the response result of subtracting");
		assertThat(response.xmlPath()
				.getInt("Envelope.Body.SubtractResponse"), is(firstNumberToSubtract - secondNumberToSubtract));
	}
}
