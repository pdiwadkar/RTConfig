package com.accion.crt.events;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;
import javax.jms.DeliveryMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class ConfigFileWatcher {
	
	
	@Autowired
	JmsTemplate template;
	
	
	@Value("${app.configFilePath}")
	private String dirPath1;
	
	private WatchService service;
	private static final String topicName="com.accion.topic.config";
	
	
	public ConfigFileWatcher() {
	
	}
	
	@PostConstruct
	public void regConfigDir() {
	
		try {
		File file = new File(dirPath1);
		File dir  = file.getParentFile();
		
		service = FileSystems.getDefault().newWatchService();
		 //System.out.println("****" + cfg.getConfigFilePath());
		//Path path = Paths.get(cfg.getConfigFilePath());
		 
		Path path = Paths.get(dir.getAbsolutePath());
		path.register(service, ENTRY_MODIFY);
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	public void watchForModifyEvent() {
		try {
		boolean poll = true;
		while(poll) {
			
			WatchKey key= service.take();
			for(WatchEvent<?> event: key.pollEvents()) {
					
				System.out.println(event.kind()+"  File: "+event.context());
				template.setDeliveryMode(DeliveryMode.PERSISTENT);
				Path path = (Path)event.context();
				if (path.toString().equals("configApp.yaml")) {
				//if(event.context().equals("configApp.yaml")) {
				template.convertAndSend(topicName,getFileInfo(event).getFileContent());
				}
			}
			poll = key.reset();					
		}
		}
		catch(InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	private FileInfo getFileInfo(WatchEvent evt) {
		
		//String fn = evt.context().toString();
		String configPath = dirPath1;
		File file = new File(configPath);
		FileInfo info = new FileInfo(file);
		return info;
	}
	
	
}
