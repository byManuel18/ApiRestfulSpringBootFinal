package com.manuel.ApiProyectoFinal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class ApiProyectoFinalApplication {

	public static void main(String[] args) {
		
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("./src/main/resources/firebaseConfig/proyectofinal-a16ca-firebase-adminsdk-vvbwe-5dea514259.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .build();
			FirebaseApp.initializeApp(options);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SpringApplication.run(ApiProyectoFinalApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/user/**").allowedOrigins("*").allowedMethods("GET","POST","DELETE","PUT").maxAge(3600);
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","DELETE","PUT").maxAge(3600);
				registry.addMapping("/signed/**").allowedOrigins("*").allowedMethods("GET","POST","DELETE","PUT").maxAge(3600);
				registry.addMapping("/meatrecord/**").allowedOrigins("*").allowedMethods("GET","POST","DELETE","PUT").maxAge(3600);
				registry.addMapping("/rawmaterialrecord/**").allowedOrigins("*").allowedMethods("GET","POST","DELETE","PUT").maxAge(3600);
			}
		};
	}

}
