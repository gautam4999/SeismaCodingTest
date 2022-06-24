package com.example.demo.services;

import com.example.demo.dto.EmployeeInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private List<EmployeeInfo> employeeInfo;

    public List<EmployeeInfo> generatePayslipInfo(List<EmployeeInfo> employees){
        System.out.println(employees);
        return employees;
    }

}
