package com.starlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarlinkManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarlinkManagerApplication.class, args);
        System.out.println("✅ Starlink Manager iniciado correctamente");
        System.out.println("🌐 API disponible en: http://localhost:8080/api");
        System.out.println("📊 H2 Console en: http://localhost:8080/h2-console");
    }
}
