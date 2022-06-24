package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


@Service
public class EmployeePaymentInfoService {
    private List<Employee> employeeInfo;
    private final Calendar calendar = Calendar.getInstance();
    private final Integer[] taxBoundaries = {0, 18200, 37000, 87000, 180000};
    private final Integer[] flatTaxCut = {0, 0, 3572, 19822, 54232};
    private final double[] percentageCut = {0, 0.19, 0.325, 0.37, 0.45};

    public List<PaymentInfo> getPayInfo(List<Employee> employees) {
        List<PaymentInfo> allEmployeePaymentInfo = new ArrayList<>();
        for (Employee employee : employees) {
            PaymentInfo paymentInfo = calculatePayInfo(employee);
            allEmployeePaymentInfo.add(paymentInfo);
        }
        return allEmployeePaymentInfo;
    }

    public PaymentInfo calculatePayInfo(Employee employee) {
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setEmployee(employee);

        calculateToAndFromDate(paymentInfo);
        calculateGrossIncome(paymentInfo);
        calculateIncomeTax(paymentInfo);
        calculateNetIncome(paymentInfo);
        calculateSuperannuation(paymentInfo);

        return paymentInfo;
    }


    public void calculateToAndFromDate(PaymentInfo paymentInfo) {
        Employee employee = paymentInfo.getEmployee();
        String month = new DateFormatSymbols().getMonths()[employee.getPaymentMonth()];
        String firstDay = "01";

        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, employee.getPaymentMonth());

        String lastDay = Integer.toString(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String fromDate = String.join(" ", firstDay, month);
        String toDate = String.join(" ", lastDay, month);

        paymentInfo.setFromDate(fromDate);
        paymentInfo.setToDate(toDate);
    }

    public void calculateGrossIncome(PaymentInfo paymentInfo) {
        Employee employee = paymentInfo.getEmployee();

        int grossIncome = Math.round((float) employee.getAnnualSalary() / 12);
        paymentInfo.setGrossIncome(grossIncome);

    }

    public void calculateIncomeTax(PaymentInfo paymentInfo) {
        Employee employee = paymentInfo.getEmployee();

        int taxableIncome = 0;
        for (int i = 0; i < taxBoundaries.length - 1; i++) {
            int annualSalary = employee.getAnnualSalary();
            if (annualSalary > taxBoundaries[i] && annualSalary < taxBoundaries[i + 1]) {
                taxableIncome += flatTaxCut[i];
                taxableIncome += Math.round((annualSalary - taxBoundaries[i]) * percentageCut[i]);
            }
        }
        paymentInfo.setIncomeTax(Math.round((float) taxableIncome / 12));
    }

    public void calculateNetIncome(PaymentInfo paymentInfo) {
        Employee employee = paymentInfo.getEmployee();
        paymentInfo.setNetIncome(paymentInfo.getGrossIncome() - paymentInfo.getIncomeTax());
    }

    public void calculateSuperannuation(PaymentInfo paymentInfo) {
        Employee employee = paymentInfo.getEmployee();
        int superannuation = Math.round(paymentInfo.getGrossIncome() * employee.getSuperRate());
        paymentInfo.setSuperannuation(superannuation);

    }
}
