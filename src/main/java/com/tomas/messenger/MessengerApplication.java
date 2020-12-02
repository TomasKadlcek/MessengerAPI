package com.tomas.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class MessengerApplication {
// if on server uncomment

	// extends SpringBootServletInitializer
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
//		return application.sources(MessengerApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

}
