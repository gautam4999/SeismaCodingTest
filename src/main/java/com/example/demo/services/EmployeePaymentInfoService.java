package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


@Service
public class EmployeePaymentInfoService {
    private List<Employee> employeeInfo;
    private final Calendar calendar = Calendar.getInstance();

    JSONParser parser = new JSONParser();
    FileReader reader = new FileReader(".\\src\\main\\resources\\TaxInfo.config.json");

    Object obj = parser.parse(reader);
    private final JSONObject taxInfoJSON = (JSONObject) obj;


    public EmployeePaymentInfoService() throws IOException, ParseException {
    }

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
        calendar.set(Calendar.DAY_OF_MONTH, 1);
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
        JSONArray taxBoundaries =  (JSONArray) this.taxInfoJSON.get("taxBoundaries");
        JSONArray flatTaxCut =  (JSONArray) this.taxInfoJSON.get("flatTaxCut");
        JSONArray percentageCut =  (JSONArray) this.taxInfoJSON.get("percentageCut");
        for (int i = 0; i < taxBoundaries.size() - 1; i++) {
            int annualSalary = employee.getAnnualSalary();
            if (annualSalary > (Long) taxBoundaries.get(i) && annualSalary < (Long) taxBoundaries.get(i + 1)) {
                taxableIncome += (Long)flatTaxCut.get(i);
                taxableIncome += Math.round((annualSalary - (Long)taxBoundaries.get(i)) * (Double)percentageCut.get(i));
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
        int superannuation = (int) Math.round(paymentInfo.getGrossIncome() * employee.getSuperRate());
        paymentInfo.setSuperannuation(superannuation);

    }
}
