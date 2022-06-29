package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import com.example.demo.entities.TaxThreshold;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


@Service
public class EmployeePaymentInfoService {
    private List<Employee> employeeInfo;
    private final Calendar calendar = Calendar.getInstance();
    private TaxThreshold[] thresholds;

    public EmployeePaymentInfoService() throws IOException, ParseException {
//        this.taxThresholdArray =
        getTaxThresholds();
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void getTaxThresholds() {
        try {
            String file = ".\\src\\main\\resources\\TaxInfo.config.json";
            String json = readFileAsString(file);
            ObjectMapper objectMapper = new ObjectMapper();
            this.thresholds = objectMapper.readValue(json, TaxThreshold[].class);

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        int annualSalary = employee.getAnnualSalary();
        TaxThreshold maxThreshold = thresholds[thresholds.length - 1];
        if (annualSalary >= maxThreshold.getLowerBound()) {
            taxableIncome += maxThreshold.getFlatTaxCut();
            taxableIncome += Math.round((annualSalary - maxThreshold.getLowerBound()) *
                    maxThreshold.getPercentageCut());
        } else {
            for (int i = 0; i < thresholds.length - 1; i++) {
                TaxThreshold currThreshold = thresholds[i];
                if (annualSalary >= currThreshold.getLowerBound() && annualSalary <= currThreshold.getUpperBound()) {
                    taxableIncome += currThreshold.getFlatTaxCut();
                    taxableIncome += Math.round((annualSalary - currThreshold.getLowerBound()) *
                            currThreshold.getPercentageCut());
                }
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
