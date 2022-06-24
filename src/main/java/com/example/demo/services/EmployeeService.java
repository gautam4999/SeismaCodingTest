package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Calendar;


@Service
public class EmployeeService {
    private List<Employee> employeeInfo;
    private final Calendar calendar = Calendar.getInstance();
    private final Integer[] taxBoundaries = {0, 18200, 37000, 87000, 180000};
    private final Integer[] flatTaxCut = {0, 0, 3572, 19822, 54232};
    private final double[] percentageCut = {0, 0.19, 0.325, 0.37, 0.45};

    public List<Employee> getPayInfo(List<Employee> employees) {

        for (Employee employee : employees) {
            PaymentInfo paymentInfo = calculatePayInfo(employee);

        }
        return employees;
    }

    public PaymentInfo calculatePayInfo(Employee employee) {
        PaymentInfo paymentInfo = new PaymentInfo();
        String month = new DateFormatSymbols().getMonths()[employee.getPaymentMonth()];
        String firstDay = "01";
        String lastDay = Integer.toString(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String fromDate = String.join(" ", firstDay, month);
        String toDate = String.join(" ", lastDay, month);

        paymentInfo.setFromDate(fromDate);
        paymentInfo.setToDate(toDate);

        int grossIncome = Math.round((float)employee.getAnnualSalary() / 12);
        paymentInfo.setGrossIncome(grossIncome);

        /*Income tax*/


        int superannuation = Math.round(paymentInfo.getGrossIncome() * employee.getSuperRate());
        paymentInfo.setSuperannuation(superannuation);

        int taxableIncome = 0;
        for (int i = 0; i < taxBoundaries.length - 1; i ++) {
            int annualSalary = employee.getAnnualSalary();
            if (annualSalary > taxBoundaries[i] && annualSalary < taxBoundaries[i+1]){
//                System.out.printf("Between %d and %d\n", taxBoundaries[i], taxBoundaries[i+1]);
                taxableIncome += flatTaxCut[i];
//                System.out.printf("Flat tax %d\n", taxableIncome);

                taxableIncome += Math.round((annualSalary - taxBoundaries[i]) * percentageCut[i]);
//                System.out.printf("Curve tax %d - %d, giving %d\n", annualSalary, taxBoundaries[i], Math.round((annualSalary - taxBoundaries[i]) * percentageCut[i]));
            }
        }

        paymentInfo.setIncomeTax(Math.round((float)taxableIncome / 12));

        paymentInfo.setNetIncome(paymentInfo.getGrossIncome() - paymentInfo.getIncomeTax());

        System.out.println(paymentInfo.toString());
        return paymentInfo;
    }

}
