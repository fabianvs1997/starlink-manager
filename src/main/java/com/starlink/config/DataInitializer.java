package com.starlink.config;

import com.starlink.entity.Equipo;
import com.starlink.entity.Pago;
import com.starlink.repository.EquipoRepository;
import com.starlink.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(EquipoRepository equipoRepository, PagoRepository pagoRepository) {
        return args -> {

            System.out.println("✅ Base de datos inicializada");
        };
    }
}
