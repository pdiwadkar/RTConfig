package com.accion.crt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class RtConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtConfigApplication.class, args);
	}

}
