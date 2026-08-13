package com.mentalhealthapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


 
public class FileUploadUtils {
    public static String saveFile(String fileName, MultipartFile multipartFile, ResourceLoader resourceLoader)
            throws IOException {
   
    	String uploadsDirPath = resourceLoader.getResource("file:src/main/resources/uploads/").getFile().getAbsolutePath();
      
        Path uploadPath = Path.of(uploadsDirPath);
        System.out.println(uploadPath);
        Path filePath;
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = UUID.randomUUID().toString();
        String sanitizedFileName = StringUtils.cleanPath(fileName);
        try (var inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve(fileCode + "-" + sanitizedFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + sanitizedFileName, ioe);
        }
        
        return fileCode + "-" + sanitizedFileName;
    }
    
    
    public static String saveExcelFile(String fileName, MultipartFile multipartFile, ResourceLoader resourceLoader)
            throws IOException {
   
    	String uploadsDirPath = resourceLoader.getResource("file:src/main/resources/uploads/mailmag/").getFile().getAbsolutePath();
      
        Path uploadPath = Path.of(uploadsDirPath);
        System.out.println(uploadPath);
        Path filePath;
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

       // String fileCode = UUID.randomUUID().toString();
        String sanitizedFileName = StringUtils.cleanPath(fileName);
        try (var inputStream = multipartFile.getInputStream()) {
            filePath = uploadPath.resolve( sanitizedFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + sanitizedFileName, ioe);
        }
        
          return uploadsDirPath+"//"+ sanitizedFileName;
    }


}

