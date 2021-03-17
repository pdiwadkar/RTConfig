package com.accion.crt.events;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class ConfigFile {
	
	private String data;
	private ByteArrayResource resource;
	
	public ByteArrayResource getFileData() {
		
		return this.resource;
	}
	public void setFileData(String path)  {
		
		try {
		File file = new File(path);
		URI uri = file.toURI();
		Path urPath = Paths.get(uri);
		this.resource = new ByteArrayResource(Files.readAllBytes(urPath)); 
		} catch(IOException io) {
			
			System.out.println("Unable to read the file"+io);
		}
		
				
	}

}
