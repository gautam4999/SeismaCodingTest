package com.example.demo.controllers;

import com.example.demo.dto.EmployeeInfo;
import com.example.demo.services.EmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/employeeInfo")
public class EmployeeInfoController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeInfoController(EmployeeService service){
        this.employeeService = service;
    }

    @PostMapping
    @ResponseBody
    public List<EmployeeInfo> processInfo(@RequestBody @Validated List<EmployeeInfo> infoRequest){
        List<EmployeeInfo>response = employeeService.generatePayslipInfo(infoRequest);

        return response;
    }
}
