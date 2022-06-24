package com.example.demo.entities;


public class Employee {

    private String firstName;
    private String lastName;
    private int annualSalary;
    private int paymentMonth;
    private double superRate;

    public Employee() {}
    public Employee(String firstName, String lastName, int annualSalary, int paymentMonth, double superRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.paymentMonth = paymentMonth;
        this.superRate = superRate;
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

    public double getSuperRate() {
        return superRate;
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

}

