package com.accion.crt.events;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInfo {
	
	private File file;
	public FileInfo(File file) {
		this.file = file;
	}
	public String getFileContent() {
		String content = "";
		try {
		 content = new String(Files.readAllBytes(Paths.get(this.file.toURI())));
		}catch(IOException io) {
			io.printStackTrace();
		}
		return content;
		
	}
	
	
}
