package io.vishal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/sftp")
public class SftpController {

    
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Working",HttpStatus.OK);
	}
	
	@GetMapping("/read")
	public ResponseEntity<String> readSftpFile(){
		
		return new ResponseEntity<String>("reading file success",HttpStatus.OK);
	}

}
