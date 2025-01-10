package com.project.luckybocky.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FCMInitializer {
    @Value("classpath:firebase/fcm_service_account.json")
    private Resource serviceAccountResource;

    @PostConstruct
    public void initialize() throws IOException {

        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = serviceAccountResource.getInputStream();



            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    // .setDatabaseUrl(databaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);

            log.info("[FirebaseApp.initializeApp]");

        }else{
            log.info("firebase init error");
        }
    }
}