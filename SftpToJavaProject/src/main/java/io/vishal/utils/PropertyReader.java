package io.vishal.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyReader {
	
	@Value("${sftp.local.host}")
	private String host;
	
	@Value("${sftp.local.port}")
	private int port;
	
	@Value("${sftp.local.user}")
	private String user;
	
	@Value("${sftp.local.password}")
	private String password;
	
	@Value("${sftp.local.folderPath}")
	private String folderPath;

}
