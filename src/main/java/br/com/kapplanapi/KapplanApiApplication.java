package br.com.kapplanapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.scheduling.annotation.EnableAsync;

@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class KapplanApiApplication {

	 

	public static void main(String[] args) {
	
		    
		SpringApplication.run(KapplanApiApplication.class, args);
		
	
	
	}
	


	@Bean
	public PasswordEncoder getPasswordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return (PasswordEncoder) encoder;
	}

}
