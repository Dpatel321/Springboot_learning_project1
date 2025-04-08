package com.designpoint.project1.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.designpoint.project1.service.FileService;


@Service
public class FileServicesimpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	      // Generate a random file name to avoid conflicts
        String originalFilename = file.getOriginalFilename();
        String randomFileName = UUID.randomUUID().toString() + "_" + originalFilename;
        
        // Create directory if it doesn't exist
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Full file path
        String filePath = path + File.separator + randomFileName;
        
        // Copy file to the target location
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        
        return randomFileName;
    }

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
	    String fullPath = path + File.separator + fileName;
	    Path filePath = Paths.get(fullPath);

	    if (!Files.exists(filePath)) {
	        throw new FileNotFoundException("File not found at path: " + fullPath);
	    }

	    try {
	        return Files.newInputStream(filePath);
	    } catch (IOException e) {
	        throw new FileNotFoundException("Unable to read file at path: " + fullPath);
	    }
	}
}
