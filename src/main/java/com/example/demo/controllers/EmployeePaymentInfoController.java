package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentInfo;
import com.example.demo.services.EmployeePaymentInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/employeePaymentInfo")
public class EmployeePaymentInfoController {
    private final EmployeePaymentInfoService employeeService;

    @Autowired
    public EmployeePaymentInfoController(EmployeePaymentInfoService service){
        this.employeeService = service;
    }




    @PostMapping
    @ResponseBody
    public List<PaymentInfo> processInfo(@RequestBody @Validated List<Employee> infoRequest){
        List<PaymentInfo> response = employeeService.getPayInfo(infoRequest);

        return response;
    }
}
