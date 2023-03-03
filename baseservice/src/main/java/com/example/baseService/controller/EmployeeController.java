package com.example.baseService.controller;

import com.example.baseService.business.EmployeeBusiness;
import com.example.baseService.common.dto.Datatable;
import com.example.baseService.common.dto.ResultInsideDTO;
import com.example.baseService.data.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/employeeController")
public class EmployeeController {
    @Autowired
    EmployeeBusiness employeeBusiness;

    @PostMapping(value = "/getEmployeeDTO")
    public ResponseEntity<Datatable> getListEmployeeDTO(@RequestBody EmployeeDTO employeeDTO) {
        Datatable datatable = employeeBusiness.getListEmployeeDTO(employeeDTO);
        return new ResponseEntity<>(datatable, HttpStatus.OK);
    }

    @GetMapping(value = "/getDetail")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@RequestParam Long employeeId) {
        EmployeeDTO employeeDTO = employeeBusiness.findEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<ResultInsideDTO> insertEmployee(@RequestBody EmployeeDTO employeeDTO) {
        ResultInsideDTO resultInsideDTO = employeeBusiness.insertEmployee(employeeDTO);
        return new ResponseEntity<>(resultInsideDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ResultInsideDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        ResultInsideDTO resultInsideDTO = employeeBusiness.updateEmployee(employeeDTO);
        return new ResponseEntity<>(resultInsideDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<ResultInsideDTO> deleteEmployeeById(@RequestParam Long employeeId) {
        ResultInsideDTO resultInsideDTO = employeeBusiness.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(resultInsideDTO, HttpStatus.OK);
    }

    //Test queryForList
    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Map<String, Object>>> getListEmployeeDTO() {
        List<Map<String, Object>> objectMap = employeeBusiness.getListEmployeeMap();
        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    //Test getListDataExport
    @PostMapping(value = "/getListDataExport")
    public ResponseEntity<List<EmployeeDTO>> getListDataExport(@RequestBody EmployeeDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOList = employeeBusiness.getListDataExport(employeeDTO);
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

}
