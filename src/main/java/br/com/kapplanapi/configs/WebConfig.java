package br.com.kapplanapi.configs;

import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
/*    */ import org.springframework.web.servlet.config.annotation.CorsRegistry;
/*    */ import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*    */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*    */ 


/*    */ @Configuration
/*    */ @EnableWebMvc
/*    */ public class WebConfig
/*    */   implements WebMvcConfigurer
/*    */ {
/*    */   public void addCorsMappings(CorsRegistry registry) {
/* 14 */     registry.addMapping("/**");
/*    */   }

@Override
public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
    configurer.favorParameter(false);
    configurer.ignoreAcceptHeader(false);
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
    configurer.mediaType("multipart/form-data", MediaType.MULTIPART_FORM_DATA);
}

/*    */ }

