package io.vishal.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SftpConnectionService {
	
	private String host = "127.0.0.1";
	private int port = 22;
	private String user = "user";
	private String password = "password";
	private String folderPath = "/sftp/";
	
	
	public SftpSession connectToSftpServer() {
		log.info("connectToSftpServer() called");
		return getDefaultSftpSessionFactory().getSession();
	}
	
	public LsEntry[] getFileEntryFromSftpRate(SftpSession session) throws IOException {
		log.info("accessing the list of files in sftp folder");
		
		return session.list(folderPath);
	}
	
	public InputStream getFileInputStreamFromSftp(ChannelSftp channel,String fileLocation) throws SftpException {
		return channel.get(fileLocation);
	}
	
	
	private DefaultSftpSessionFactory getDefaultSftpSessionFactory() {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
		
		factory.setHost(host);
		factory.setPort(port);
		factory.setUser(user);
		factory.setPassword(password);
		factory.setAllowUnknownKeys(true);
		
		return factory;
		
	}
}
