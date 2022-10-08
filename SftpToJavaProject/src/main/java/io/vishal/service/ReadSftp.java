package io.vishal.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReadSftp {
	
	@Autowired
	private SftpConnectionService sftpConnectionService;
	
	

	public void startReadingSftpFile() {
        log.info("connection to sftp server");		
		
		SftpSession session = null;
		ChannelSftp channel = null;
		
		try {
			session = sftpConnectionService.connectToSftpServer();
			channel = session.getClientInstance();
			
		} catch (Exception e) {
			log.error("exception occured while connectinf to sftp server");
		} 
		finally {
			closeSftpConnection(session, channel);
		}
	}
	
	
	private void readFileFromServer(ChannelSftp channelSftp, SftpSession sftpSession) {
		log.info("read file from server called");
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		List<String> filesList;
		String fileName = null;
		
		try {
			
		}
		catch (Exception e) {
			log.error("exception occured while reading file from server :: "+e);
		}
		finally {
			try {
				if(bufferedReader != null && inputStream != null) {
					bufferedReader.close();
					inputStream.close();
				}
			}
			catch(Exception e){
				log.error("exxception occured while closing the resouces :: "+e);
			}
		}
	}
	
	
	private void closeSftpConnection(SftpSession session, ChannelSftp channel) {
		log.info("closing sftp session");
		if(session != null && channel != null) {
			channel.disconnect();
			session.close();
		}
	}
}
