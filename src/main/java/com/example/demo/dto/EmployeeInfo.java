package com.example.demo.dto;

public class EmployeeInfo {

    private String firstName;
    private String lastName;
    private int annualSalary;
    private int paymentMonth;
    private float superRate;

    public EmployeeInfo(String firstName, String lastName, int annualSalary, int paymentMonth, float superRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.paymentMonth = paymentMonth;
        this.superRate = superRate;
    }

    @Override
    public String toString() {
        return "Info{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", annualSalary=" + annualSalary +
                ", paymentMonth=" + paymentMonth +
                ", superRate=" + superRate +
                '}';
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAnnualSalary() {
        return annualSalary;
    }

    public int getPaymentMonth() {
        return paymentMonth;
    }

    public float getSuperRate() {
        return superRate;
    }
}
