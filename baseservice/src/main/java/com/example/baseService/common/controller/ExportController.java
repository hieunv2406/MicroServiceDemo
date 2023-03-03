package com.example.baseService.common.controller;

import com.example.baseService.business.EmployeeBusiness;
import com.example.baseService.common.utils.FileUtil;
import com.example.baseService.data.dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Slf4j
@RestController
@RequestMapping(value = "/commonExport")
public class ExportController {
    @Autowired
    private EmployeeBusiness employeeBusiness;

    @GetMapping("/export")
    public ResponseEntity<Resource> getFile(@RequestParam String key) throws Exception {
        File file = null;
        switch (key) {
            case "EMPLOYEE_MANAGER":
                EmployeeDTO employeeDTO = new EmployeeDTO();
                file = employeeBusiness.exportData(employeeDTO);
                break;
            case "EMPLOYEE_MANAGER1":
//                EmployeeDTO employeeDTO1 = new EmployeeDTO();
//                file = employeeBusiness.exportData(employeeDTO1);
                break;
            default:
        }
        if (file != null) {
            return FileUtil.responseFormFile(file);
        }
        return null;
    }
}