package com.blogger.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MyUtility {

	private String uploadPath;
	
	public MyUtility()throws IOException {
//		uploadPath= "C:\\Users\\orbad\\Documents\\workspace-spring-tool-suite-4-4.21.0.RELEASE\\Blogger\\src\\main\\resources\\static\\images";
		uploadPath= new ClassPathResource("static/images").getFile().getAbsolutePath();
	}
	public  String generateHash(String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return hashedPassword;
	}
	
	public boolean verifyPassword(String oldPassword,String newPassword) {
		return BCrypt.checkpw(newPassword,oldPassword);
	}
	
	public String saveImage(MultipartFile file) throws IOException {
        String oldName = file.getOriginalFilename();
        String fileExtension = oldName.substring(oldName.lastIndexOf("."));
        String newFileName = generateNewFileName() + fileExtension;

        File targetFile = new File(uploadPath, newFileName);
        file.transferTo(targetFile);
        return newFileName;
    }
	public String generateNewFileName() {
		return "Image_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")).toString();
		
	}
	
	public void deleteImage(String imageName) {
		File targetFile = new File(uploadPath, imageName);
		targetFile.delete();
	}
	
	
}
