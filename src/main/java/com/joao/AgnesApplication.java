package com.joao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.joao.*" })
public class AgnesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgnesApplication.class, args);
	}

}
