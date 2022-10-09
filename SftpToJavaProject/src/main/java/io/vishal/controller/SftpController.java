package io.vishal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("Working", HttpStatus.OK);
	}

	@GetMapping(value = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> readSftpFile() {
		log.info("read sftp file controller called");
		List<String> fileContents = readSftpService.startReadingSftpFile();
		return new ResponseEntity<>(fileContents, HttpStatus.OK);
	}

}
