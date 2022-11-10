package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.FileIoService;

@Controller
public class FileIOController {

	@Autowired
	private FileIoService fileIoService;
	
	
	@PostMapping(path="/uploadfile")
	public String uploadFiles(@RequestParam("file") MultipartFile inputFile) {
		
		try {
			fileIoService.uploadFiles(inputFile);
		} catch (Exception e) {
			return "Failed to upload a file because of "+e.getMessage();
		}
		
		
		return "redirect:/uploadfile";
	}
	
	@GetMapping(path="/uploadfile")
	public String listOfFiles(Model model) {
		
		List<String>files=fileIoService.getAllCurrentFilesInFolder();
		System.out.println(files.size());
		model.addAttribute("files", files.size()==0?"":files);
		return "index";
	}
	
	
	
	
}
