package com.focowell.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.focowell.config.error.StorageException;
import com.focowell.service.FileService;

@Service(value = "fileService")
public class FileServiceImp implements FileService {
	
	@Value("${upload.path}")
	private String path;
	
	@Override
	public void uploadFile(MultipartFile file) throws StorageException  {

		
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        try {
        	File directory = new File(path);
    	    if (! directory.exists()){
    	        directory.mkdirs();
    	        // If you require it to make the entire directory path including parents,
    	        // use directory.mkdirs(); here instead.
    	    }
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            String msg = String.format("Failed to store file", file.getName());

            throw new StorageException(msg, e);
        }
	
	 }
	@Override
	public void deleteFile(String fileName) {
		
        if (fileName==null) 
        	return;

        File file = new File(path + fileName);
        file.delete();
    
	
	 }
	@Override
	public File getFile(String fileName) {
		
        if (fileName==null) 
        	return null;

        return new File(path + fileName);
        	
	 }
	
	@Override
	public String getFileNameFromPath(String path) {
		String separator = "\\";
		String[] segments =path.split(Pattern.quote(separator));
		return segments[segments.length-1];
	}
}
