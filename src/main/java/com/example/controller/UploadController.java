package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {

	private static final String uploadDir = "C:/uploads/";
	
	@PostMapping
	@ResponseBody
	public String upload(MultipartFile file) throws IllegalStateException, IOException {
		
		String fileName = file.getOriginalFilename();

		File uploadFile = new File(uploadDir + fileName);
		
		file.transferTo(uploadFile);
		
		return "upload success";
	}
	
	@PostMapping("/files")
	@ResponseBody
	public String upload(List<MultipartFile> files) throws IllegalStateException, IOException {
		
		for(MultipartFile file:files) {
			
			String fileName = file.getOriginalFilename();

			File uploadFile = new File(uploadDir + fileName);
			
			file.transferTo(uploadFile);
		}
		
		return "upload multiple files success";
	}
}
