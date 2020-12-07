package com.example.admin.common.controller;

import com.example.admin.business.EmployeeBusiness;
import com.example.admin.common.service.FilesStorageService;
import com.example.admin.data.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
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

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;

@Log4j2
@RestController
@RequestMapping(value = "/commonExport")
public class ExportController {
    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    private ServletContext servletContext;
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
            default:
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        MediaType mediaType = getMediaTypeForFileName(servletContext, file.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(mediaType)
                .body(resource);
    }

    private MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            log.info(e.getMessage());
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}