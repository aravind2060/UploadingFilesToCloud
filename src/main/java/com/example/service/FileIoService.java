package com.example.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileIoService {

	private Path rootLocation=Paths.get("src/main/resources/upload-dir/");

	public String uploadFiles(MultipartFile inputFile) throws Exception {
		
		return storeAFile(inputFile);
	}
	
	public String storeAFile(MultipartFile inputFile) throws Exception {
		
		System.out.println("Inside store file");
		
		try {
			if (inputFile.isEmpty()) {
				throw new Exception("Failed to store empty file.");
			}
			Path destinationFile = rootLocation.resolve(
					Paths.get(inputFile.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new Exception(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = inputFile.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);	
			  System.out.println("file uploaded sucessfully");	
			}
			return "Successfully stored the file";
		}
		catch (IOException e) {
			throw new Exception("Failed to store file.", e);
		}
		
	}
	
	public List<String> getAllCurrentFilesInFolder(){
		
		File f=new File(rootLocation.toString());
		String list[]=f.list();
		
		return Arrays.asList(list.length==0?new String[] {""}:list);
	}
}
