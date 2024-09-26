package br.com.kapplanapi.configs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	 @Bean
	    public JavaMailSender comprasMailSender() {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("nspro38.hostgator.com.br");
	        mailSender.setPort(587);
	        mailSender.setUsername("compras@gruporosinetos.com");
	        mailSender.setPassword("Info999@@456"); // Coloque a senha correta aqui
	        return mailSender;
	    }

	    @Bean
	    public JavaMailSender notificacoesMailSender() {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("nspro38.hostgator.com.br");
	        mailSender.setPort(587);
	        mailSender.setUsername("notifications.gpros@gruporosinetos.com");
	        mailSender.setPassword("TIt@niwm2014"); // Coloque a senha correta aqui
	        return mailSender;
	    }
	
}
