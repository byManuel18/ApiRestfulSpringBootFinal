package com.manuel.ApiProyectoFinal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class ApiProyectoFinalApplication {

	public static void main(String[] args) {
		///ApiProyectoFinal/src/main/resources/firebaseConfig/proyectofinal-a16ca-firebase-adminsdk-vvbwe-621b2585d4.json
		
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

}
