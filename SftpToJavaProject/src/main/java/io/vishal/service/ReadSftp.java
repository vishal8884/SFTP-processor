package io.vishal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;

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
			readFileFromServer(channel, session);
			
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
			ChannelSftp.LsEntry[] lsEntries = sftpConnectionService.getFileEntryFromSftpRate(sftpSession);
			
			Comparator<LsEntry> comparator = Comparator.comparing(ls -> ls.getAttrs().getMTime());
			List<LsEntry> sortedLsEntries = Arrays.asList(lsEntries).stream().sorted(comparator).collect(Collectors.toList());
			
			filesList = sortedLsEntries.stream().map(ls -> ls.getFilename()).collect(Collectors.toList());
			
			log.debug("filesList :: "+filesList);
			
			if(filesList.isEmpty()) {
				log.info("no files found to read");
			}
			else {
				for(ChannelSftp.LsEntry lsEntry : sortedLsEntries) {
					fileName = lsEntry.getFilename();
					if(!fileName.endsWith(".txt")) {
						log.error("invalid file type please use .txt files");
						continue;
					}
					String fileLocation = String.format("%s%s","/sftp/",fileName);
					log.debug("filename :: "+fileName+"       fileLocation :: "+fileLocation);
					
					inputStream = sftpConnectionService.getFileInputStreamFromSftp(channelSftp, fileLocation);
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					readFileLineByLine(bufferedReader, fileName);
				}
			}
			
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
	
	private void readFileLineByLine(BufferedReader bufferedReader, String fileName) throws IOException {
		String line = null;
		
		while((line = bufferedReader.readLine()) != null) {
			log.debug("line :: "+line);
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
