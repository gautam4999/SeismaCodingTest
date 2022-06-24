package com.example.demo.controllers;

import com.example.demo.dto.EmployeeInfoDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/employeeInfo")
public class EmployeeInfoController {

    @PostMapping
    @ResponseBody
    public EmployeeInfoDTO processInfo(@RequestBody @Validated EmployeeInfoDTO infoRequest){
        System.out.println(infoRequest.toString());
        return infoRequest;
    }
}
