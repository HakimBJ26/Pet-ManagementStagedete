package com.example.PetgoraBackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        Dotenv dotenv = Dotenv.configure().load();
        logger.info("Loaded FIREBASE_PROJECT_ID: {}", dotenv.get("FIREBASE_PROJECT_ID"));


        String type = dotenv.get("FIREBASE_TYPE");
        String projectId = dotenv.get("FIREBASE_PROJECT_ID");
        String privateKeyId = dotenv.get("FIREBASE_PRIVATE_KEY_ID");
        String privateKey = dotenv.get("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");
        String clientEmail = dotenv.get("FIREBASE_CLIENT_EMAIL");
        String clientId = dotenv.get("FIREBASE_CLIENT_ID");
        String authUri = dotenv.get("FIREBASE_AUTH_URI");
        String tokenUri = dotenv.get("FIREBASE_TOKEN_URI");
        String authProviderCertUrl = dotenv.get("FIREBASE_AUTH_PROVIDER_X509_CERT_URL");
        String clientCertUrl = dotenv.get("FIREBASE_CLIENT_X509_CERT_URL");

        String firebaseConfig = String.format(
                "{\n" +
                        "  \"type\": \"%s\",\n" +
                        "  \"project_id\": \"%s\",\n" +
                        "  \"private_key_id\": \"%s\",\n" +
                        "  \"private_key\": \"%s\",\n" +
                        "  \"client_email\": \"%s\",\n" +
                        "  \"client_id\": \"%s\",\n" +
                        "  \"auth_uri\": \"%s\",\n" +
                        "  \"token_uri\": \"%s\",\n" +
                        "  \"auth_provider_x509_cert_url\": \"%s\",\n" +
                        "  \"client_x509_cert_url\": \"%s\"\n" +
                        "}", type, projectId, privateKeyId, privateKey, clientEmail, clientId, authUri, tokenUri, authProviderCertUrl, clientCertUrl
        );

        FirebaseOptions options;
        try (ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8))) {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            logger.error("Failed to initialize Firebase options with the provided credentials.", e);
            throw e;
        }

        FirebaseApp firebaseApp;
        try {
            firebaseApp = FirebaseApp.initializeApp(options);
            logger.info("Firebase successfully initialized.");
        } catch (Exception e) {
            logger.error("Failed to initialize FirebaseApp.", e);
            throw e;
        }

        return firebaseApp;
    }
}
