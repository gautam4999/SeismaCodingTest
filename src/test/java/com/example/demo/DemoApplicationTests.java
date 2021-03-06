package com.example.demo;

import com.example.demo.controllers.EmployeePaymentInfoController;
import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import com.example.demo.services.EmployeePaymentInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {
	private final EmployeePaymentInfoService service;

	@Autowired
	public DemoApplicationTests(EmployeePaymentInfoService service){
		this.service = service;
	}

	@Test

	void test1() {
		Employee testEmployee = new Employee("David", "Rudd", 60050, 1, 0.09);

		try {
			EmployeePaymentInfoService service = new EmployeePaymentInfoService();
		}catch(Exception e) {
			e.printStackTrace();
		}

		PaymentInfo testPaymentInfo = service.calculatePayInfo(testEmployee);

		assertEquals(testPaymentInfo.getFromDate(), "01 February");
		assertEquals(testPaymentInfo.getToDate(), "28 February");
		assertEquals(testPaymentInfo.getGrossIncome(), 5004);
		assertEquals(testPaymentInfo.getIncomeTax(), 922);
		assertEquals(testPaymentInfo.getSuperannuation(), 450);
		assertEquals(testPaymentInfo.getNetIncome(), 4082);
	}

	@Test
	void test2() {
		Employee testEmployee = new Employee("Ryan", "Chen", 120000, 1, 0.1);
		try {
			EmployeePaymentInfoService service = new EmployeePaymentInfoService();
		}catch(Exception e) {
			e.printStackTrace();
		}

		PaymentInfo testPaymentInfo = service.calculatePayInfo(testEmployee);

		assertEquals(testPaymentInfo.getFromDate(), "01 February");
		assertEquals(testPaymentInfo.getToDate(), "28 February");
		assertEquals(testPaymentInfo.getGrossIncome(), 10000);
		assertEquals(testPaymentInfo.getIncomeTax(), 2669);
		assertEquals(testPaymentInfo.getSuperannuation(), 1000);
		assertEquals(testPaymentInfo.getNetIncome(), 7331);
	}


}
