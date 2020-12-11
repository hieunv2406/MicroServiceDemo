package com.example.admin.common.controller;

import com.example.admin.business.EmployeeBusiness;
import com.example.admin.data.dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;

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
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            MediaType mediaType = getMediaTypeForFileName(file.getName());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(mediaType)
                    .body(resource);
        }
        return null;
    }

    private MediaType getMediaTypeForFileName(String fileName) {
        String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(mimeType);
            return mediaType;
        } catch (Exception e) {
            log.info(e.getMessage());
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}