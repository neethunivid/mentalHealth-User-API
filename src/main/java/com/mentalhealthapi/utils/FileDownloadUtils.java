package com.mentalhealthapi.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class FileDownloadUtils {
	 private static final Logger logger = LoggerFactory.getLogger(FileDownloadUtils.class);
	    private static final Path UPLOAD_DIR = Paths.get("src/main/resources/uploads");
	    private Path foundFile;
     
	    public Resource getFileAsResource(String fileCode) throws IOException {
	        logger.info("Searching for files in directory: {}", UPLOAD_DIR.toAbsolutePath());
	        foundFile = null; // Reset foundFile for each call
	        Files.list(UPLOAD_DIR).forEach(file -> {
	            logger.debug("Checking file: {}", file.getFileName().toString());
	            if (file.getFileName().toString().startsWith(fileCode)) {
	                foundFile = file;
	                logger.info("Found matching file: {}", foundFile.toAbsolutePath());
	                return;
	            }
	        });

	        if (foundFile != null) {
	            return new UrlResource(foundFile.toUri());
	        } else {
	            logger.warn("No file found for file code: {}", fileCode);
	            return null;
	        }
	    }
    

}





 
//public class FileDownloadUtils {
//    private Path foundFile;
//     
//    public Resource getFileAsResource(String fileCode) throws IOException {
//        Path dirPath = Paths.get("uploads");
//        Files.list(dirPath).forEach(file -> {
//            if (file.getFileName().toString().startsWith(fileCode)) {
//                foundFile = file;
//                return;
//            }
//        });
// 
//        if (foundFile != null) {
//            return new UrlResource(foundFile.toUri());
//        }
//         
//        return null;
//    }
//
//}
