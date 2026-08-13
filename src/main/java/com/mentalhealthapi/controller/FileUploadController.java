package com.mentalhealthapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.helper.FileUploadResponse;
import com.mentalhealthapi.service.FileUploadService;
import com.mentalhealthapi.service.GrantRequestService;
import com.mentalhealthapi.utils.FileDownloadUtils;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {
   
    private FileUploadService fileUploadService;
    
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    /**
     * api to upload image
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponse result = fileUploadService.uploadFile2(file);
            ApiResponse response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    /**
     * Api to download image
     * @param fileCode
     * @return
     */
    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
    
    	Resource resource=fileUploadService.downloadFile(fileCode);
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);       
    }
   
    /**
     * Upload excel file to mail mag job table
     * */
    
    @PostMapping("/uploadcsv")
    public ResponseEntity<ApiResponse<String>> uploadExcelFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("topic") String topic, @RequestParam("type") String type,@RequestParam("format") String format) {
    	try {
    		 String result = fileUploadService.uploadExcel(file,topic,type,format);
    		 ApiResponse response = new ApiResponse<>(1, null, result);
             return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    	
    	
    }
       
    
    
}
