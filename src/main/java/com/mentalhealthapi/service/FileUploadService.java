package com.mentalhealthapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDateTime;
import org.apache.poi.ss.usermodel.*;

import com.mentalhealthapi.helper.FileUploadResponse;
import com.mentalhealthapi.model.Uploads;
import com.mentalhealthapi.model.MailMagJob;
import com.mentalhealthapi.model.MailMagazineEmail;
import com.mentalhealthapi.repository.MailMagJobRepository;
import com.mentalhealthapi.repository.MailmagazineEmailRepository;
import com.mentalhealthapi.repository.UploadsRepository;
import com.mentalhealthapi.utils.FileDownloadUtils;
import com.mentalhealthapi.utils.FileUploadUtils;
import org.springframework.core.io.ResourceLoader;
import java.io.FileInputStream;
import java.io.FileReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;
@Service
public class FileUploadService {
	private UploadsRepository uploadsRepository;
	private ResourceLoader rs;
	private MailMagJobRepository mailMagJobRepository;
	private MailmagazineEmailRepository mailMagazineEmailRepository;
	@Autowired
	public FileUploadService(UploadsRepository uploadsRepository,ResourceLoader rs,MailMagJobRepository mailMagJobRepositor,
			MailmagazineEmailRepository mailMagazineEmailRepository) {
		this.uploadsRepository = uploadsRepository;
		this.rs = rs;
		this.mailMagJobRepository = mailMagJobRepositor;
		this.mailMagazineEmailRepository = mailMagazineEmailRepository;
	}

	public String uploadFile(MultipartFile file) {
		try {
			// Get the original file name
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			// Create a new instance of Uploads
			Uploads upload = new Uploads();
			upload.setFileName(fileName);
			upload.setUploadTime(LocalDateTime.now());

			// Save the Upload object to the database
			uploadsRepository.save(upload);

			// Save the file to disk
			String uploadDir = "uploads";
			Path uploadPath = Path.of(uploadDir);

			// Create the upload directory if it doesn't exist
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Generate a unique file name by appending the current timestamp to the
			// original file name
			String uniqueFileName = LocalDateTime.now().toString() + "-" + fileName;

			// Save the file to the upload directory
			Path filePath = uploadPath.resolve(uniqueFileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return "File uploaded successfully";
		} catch (Exception e) {
			return "Failed to upload file";
		}
	}

	public FileUploadResponse uploadFile2(MultipartFile multipartFile) {
		FileUploadResponse response = new FileUploadResponse();
		try {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			long size = multipartFile.getSize();
			
		
			String filecode = FileUploadUtils.saveFile(fileName, multipartFile,rs);

			// return new ResponseEntity<>(response, HttpStatus.OK);
			// Create a new instance of Uploads
			Uploads upload = new Uploads();
			upload.setFileName(filecode);
			upload.setUploadTime(LocalDateTime.now());
			uploadsRepository.save(upload);

			response.setFilename(fileName);
			response.setMessage("File uploaded successfully");
			response.setId(upload.getId());

			return response;
			// return "File uploaded successfully";
		} catch (Exception e) {
			response.setMessage("Failed to upload file");
			return response;
		}
	}

	public Resource downloadFile(String fileCode) {
		FileDownloadUtils downloadUtil = new FileDownloadUtils();
		Resource resource = null;
		try {
			resource = downloadUtil.getFileAsResource(fileCode);
		} catch (IOException e) {
			// return ResponseEntity.internalServerError().build();
		}
		return resource;
	}
	public Uploads getFileName(Long id) {
		return uploadsRepository.getById(id);
	}

	public String uploadExcel(MultipartFile file, String topic, String type,String format) {
	    try {
	    	 String fileName = file.getOriginalFilename();
	    	 String filecode = FileUploadUtils.saveExcelFile(fileName, file,rs);	
	    	 int recd = this.checkTopicExists(topic);
	    	 if (recd>0) {
	    		 return "Error topic already exist";
	    	 }else {
	        // Save data to the database
	        MailMagJob mailMagJob = new MailMagJob();
	        mailMagJob.setFileName(filecode);
	        mailMagJob.setTopic(topic);
	        mailMagJob.setType(type);
	        mailMagJob.setDate(LocalDateTime.now());
	        MailMagJob mailMag = mailMagJobRepository.save(mailMagJob);
	        Integer mailmagId=mailMag.getId();
	        if (format.equals("excel"))  {            	
            	this.excelParser(filecode, mailmagId, type);
            }
	        if (format.equals("csv"))  {            
            	this.csvParser(filecode, mailmagId, type);
            }
	        return "File uploaded and data saved successfully.";
	    	 }
	    } catch (IOException e) {
	        return "Error uploading file: " + e.getMessage();
	    }
	}
	
	
	public void excelParser(String uploadFilePath, int mailMagId, String type) {
	    try {
	    	 FileInputStream fis = new FileInputStream(uploadFilePath);
	         XSSFWorkbook workbook = new XSSFWorkbook(fis);

	        int totalSheets = workbook.getNumberOfSheets();
	        for (int i = 0; i < totalSheets; i++) {
	            Sheet sheet = workbook.getSheetAt(i);
	            for (Row row : sheet) {
	                Cell nameCell = row.getCell(0);
	                Cell emailCell = row.getCell(1);
	                String name = nameCell != null ? nameCell.getStringCellValue() : "";
	                String email = emailCell != null ? emailCell.getStringCellValue() : "";
	                this.saveMailId(name, email, mailMagId);
	            }
	        }
	        workbook.close();
	        fis.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void csvParser(String uploadFilePath, int mailMagId, String type) {
        try (CSVReader reader = new CSVReader(new FileReader(uploadFilePath))) {
            String[] line;
            int row = 0;
            while ((line = reader.readNext()) != null) {
                row++;
                String name = line[0];
				String email = line[1];
				this.saveMailId(name, email, mailMagId);
            }
        }catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
	

	
	
	
	

	public void saveMailId(String name, String email, int mailMagId) {
	    try {
	        MailMagazineEmail mailMagazineEmail = new MailMagazineEmail();
	        mailMagazineEmail.setName(name);
	        mailMagazineEmail.setEmail(email);
	        mailMagazineEmail.setMailMagJob(mailMagId);
	        mailMagazineEmail.setCreatedDate(LocalDateTime.now());
	        mailMagazineEmail.setSendStatus("PENDING");
	        mailMagazineEmailRepository.save(mailMagazineEmail);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public int checkTopicExists(String topic) {
        return mailMagJobRepository.countByTopic(topic);
    }


}
