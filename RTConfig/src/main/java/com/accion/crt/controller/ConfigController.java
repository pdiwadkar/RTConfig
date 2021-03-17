package com.accion.crt.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.accion.crt.events.ConfigFile;
import com.accion.crt.events.ConfigFileEvent;

@RestController
public class ConfigController {
	
	@Autowired
	ConfigFile conf;
	
	@Value("${app.configFilePath}")
	private String configFilePath;
		
	/*@RequestMapping("/")
	String index() {
		return "File server UP!!" ;
	}*/
	
	
	@RequestMapping(path="/downloadConfigFile",method=RequestMethod.GET)
	public ResponseEntity<Resource> getConfigFile() throws IOException{
		
		HttpHeaders header = new HttpHeaders();
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
       header.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
         conf.setFileData(configFilePath);
        //System.out.println("Config file path: "+appConfig.getConfigFilePath());
        ByteArrayResource br = conf.getFileData();
		return ResponseEntity.ok().headers(header).body(br);
	}
	

}
