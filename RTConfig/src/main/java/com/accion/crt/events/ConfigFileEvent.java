package com.accion.crt.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ConfigFileEvent {
	
	/*
	 * Load the file after server starts.
	 */
	@Autowired
	private ConfigFile configFile;
	
	
	@Autowired
	private ConfigFileWatcher watcher;
	
	@Value("${app.configFilePath}")
	private String configFilePath;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadFileAfterStartup() {
		System.out.println("File loaded "+configFilePath);
		configFile.setFileData(configFilePath);
		watcher.watchForModifyEvent();
	}
	//Application fails to start.
	@EventListener(ApplicationFailedEvent.class)
	public void loadFileFailed() {
		
		System.out.println("Failed to start the application..");
		
	}
	
	
}
