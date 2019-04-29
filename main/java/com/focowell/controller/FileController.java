package com.focowell.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.config.error.StorageException;
import com.focowell.model.ActionEvent;
import com.focowell.service.ActionEventService;
import com.focowell.service.FileService;
import com.focowell.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    
 
    @RequestMapping(value="/files", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public void uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("prevFileName") String prevFileName) throws StorageException {
    	fileService.deleteFile(prevFileName);
    	fileService.uploadFile(file);
    }
  

    @RequestMapping(value = "/files/{fileName}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "fileName") String fileName){
    	fileService.deleteFile(fileName);
    }
   
    
}
