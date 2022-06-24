package com.example.demo.controllers;

import com.example.demo.entities.Employee;
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
    public List<Employee> processInfo(@RequestBody @Validated List<Employee> infoRequest){
        List<Employee>response = employeeService.getPayInfo(infoRequest);

        return response;
    }
}
