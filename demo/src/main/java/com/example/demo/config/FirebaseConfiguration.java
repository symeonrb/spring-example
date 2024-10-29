package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfiguration.class);

    @Value("classpath:firebase-adminsdk.json")
    Resource serviceAccount;

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @PostConstruct
    FirebaseAuth initialisationFirebaseAuth() throws IOException {
        logger.info("FirebaseConfiguration initialisationFirebaseAuth");

        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setStorageBucket("spring-example-1e6b0.appspot.com")
                .build();

        var firebaseApp = FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(firebaseApp);
    }
}