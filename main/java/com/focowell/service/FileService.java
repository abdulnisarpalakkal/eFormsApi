package com.focowell.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.focowell.config.error.StorageException;

public interface FileService {
	void uploadFile(MultipartFile file) throws StorageException;
	void deleteFile(String fileName);
	File getFile(String fileName);
	String getFileNameFromPath(String path);

}
