package io.vishal.service;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SftpConnectionService {
	
	public SftpSession connectToSftpServer() {
		log.info("connectToSftpServer() called");
		return getDefaultSftpSessionFactory().getSession();
	}
	
	
	
	private DefaultSftpSessionFactory getDefaultSftpSessionFactory() {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
		
		factory.setHost("127.0.0.1");
		factory.setPort(22);
		factory.setUser("user");
		factory.setPassword("password");
		factory.setAllowUnknownKeys(true);
		
		return factory;
		
	}
}
