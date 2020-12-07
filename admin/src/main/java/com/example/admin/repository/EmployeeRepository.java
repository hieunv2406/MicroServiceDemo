package com.example.admin.repository;

import com.example.admin.common.dto.Datatable;
import com.example.admin.common.dto.ResultInsideDTO;
import com.example.admin.data.dto.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {

    EmployeeDTO findEmployeeById(Long employee_id);

    ResultInsideDTO insertEmployee(EmployeeDTO employeeDTO);

    ResultInsideDTO updateEmployee(EmployeeDTO employeeDTO);

    ResultInsideDTO deleteEmployeeById(Long employeeId);

    Datatable getListEmployeeDTO(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getListDataExport(EmployeeDTO employeeDTO);

    List<Map<String, Object>> getListEmployeeMap();
}
