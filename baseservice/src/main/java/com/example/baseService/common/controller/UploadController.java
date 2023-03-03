package com.example.baseService.common.controller;

import com.example.baseService.common.dto.FileDTO;
import com.example.baseService.common.dto.ResultInsideDTO;
import com.example.baseService.common.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/commonUpload")
public class UploadController {

    @Autowired
    FilesStorageService filesStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ResultInsideDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        ResultInsideDTO resultInsideDTO = new ResultInsideDTO();
        try {
            filesStorageService.store(file);
            resultInsideDTO.setMessages("Uploaded the file successfully: " + file.getOriginalFilename());
            return new ResponseEntity<>(resultInsideDTO, HttpStatus.OK);
        } catch (Exception e) {
            resultInsideDTO.setMessages("Could not upload the file: " + file.getOriginalFilename() + "!");
            return new ResponseEntity<>(resultInsideDTO, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //Hàm lấy thông tin file
    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getListFiles() {
        List<FileDTO> fileInfos = filesStorageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String fileType = null;
            String url = MvcUriComponentsBuilder
                    .fromMethodName(UploadController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileDTO(filename, fileType, url);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(fileInfos, HttpStatus.OK);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    //hàm upload file kiểu khác
    @PostMapping(value = "/uploadFile")
    public String uploadFile(FileDTO dto) {
//        model.addAttribute("message", "Upload success");
//        model.addAttribute("description", myFile.getDescription());
        try {
            MultipartFile multipartFile = null;
//            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(this.getFolderUpload(), fileName);
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
//            model.addAttribute("message", "Upload failed");
        }
        return "result";
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

}
