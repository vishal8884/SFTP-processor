package io.vishal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vishal.service.ReadSftpService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/v1/sftp")
@Slf4j
public class SftpController {

	@Autowired
	private ReadSftpService readSftpService;
    
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Working",HttpStatus.OK);
	}
	
	@GetMapping("/read")
	public ResponseEntity<String> readSftpFile(){
		log.info("read sftp file controller called");
		readSftpService.startReadingSftpFile();
		return new ResponseEntity<String>("reading file success",HttpStatus.OK);
	}

}
