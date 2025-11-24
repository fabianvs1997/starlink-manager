package com.starlink.listener;

import com.starlink.entity.Equipo;
import com.starlink.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

/**
 * Listener JPA para cifrado/descifrado automático de datos sensibles
 * Se ejecuta antes y después de operaciones de base de datos
 */
@Component
public class EquipoEncryptionListener {

    private static CryptoService cryptoService;

    /**
     * Inyección de dependencia del servicio de cifrado
     * Usa setter estático porque JPA crea instancias sin Spring
     */
    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        EquipoEncryptionListener.cryptoService = cryptoService;
    }

    /**
     * Cifrar datos sensibles ANTES de guardar en DB
     * Se ejecuta en INSERT y UPDATE
     */
    @PrePersist
    @PreUpdate
    public void encryptData(Equipo equipo) {
        if (cryptoService == null) {
            throw new IllegalStateException("CryptoService no está inicializado");
        }

        // Cifrar nombre
        if (equipo.getNombreDescifrado() != null) {
            equipo.setNombre(cryptoService.encrypt(equipo.getNombreDescifrado()));
        }

        // Cifrar correo
        if (equipo.getCorreoDescifrado() != null) {
            equipo.setCorreo(cryptoService.encrypt(equipo.getCorreoDescifrado()));
        }

        // Cifrar contraseña
        if (equipo.getContraseñaDescifrado() != null) {
            equipo.setContraseña(cryptoService.encrypt(equipo.getContraseñaDescifrado()));
        }

        // Cifrar cuenta/tarjeta
        if (equipo.getCuentaTarjetaDescifrado() != null) {
            equipo.setCuentaTarjeta(cryptoService.encrypt(equipo.getCuentaTarjetaDescifrado()));
        }

        // Cifrar número ID
        if (equipo.getNumeroIdDescifrado() != null) {
            equipo.setNumeroId(cryptoService.encrypt(equipo.getNumeroIdDescifrado()));
        }

        // Cifrar número de serie
        if (equipo.getNumeroSerieDescifrado() != null) {
            equipo.setNumeroSerie(cryptoService.encrypt(equipo.getNumeroSerieDescifrado()));
        }

        // Cifrar número de kit
        if (equipo.getNumeroKitDescifrado() != null) {
            equipo.setNumeroKit(cryptoService.encrypt(equipo.getNumeroKitDescifrado()));
        }

        // Cifrar notas
        if (equipo.getNotasDescifrado() != null) {
            equipo.setNotas(cryptoService.encrypt(equipo.getNotasDescifrado()));
        }
    }

    /**
     * Descifrar datos sensibles DESPUÉS de cargar desde DB
     * Se ejecuta después de SELECT
     */
    @PostLoad
    public void decryptData(Equipo equipo) {
        if (cryptoService == null) {
            throw new IllegalStateException("CryptoService no está inicializado");
        }

        // Descifrar nombre
        if (equipo.getNombre() != null) {
            equipo.setNombreDescifrado(cryptoService.decrypt(equipo.getNombre()));
        }

        // Descifrar correo
        if (equipo.getCorreo() != null) {
            equipo.setCorreoDescifrado(cryptoService.decrypt(equipo.getCorreo()));
        }

        // Descifrar contraseña
        if (equipo.getContraseña() != null) {
            equipo.setContraseñaDescifrado(cryptoService.decrypt(equipo.getContraseña()));
        }

        // Descifrar cuenta/tarjeta
        if (equipo.getCuentaTarjeta() != null) {
            equipo.setCuentaTarjetaDescifrado(cryptoService.decrypt(equipo.getCuentaTarjeta()));
        }

        // Descifrar número ID
        if (equipo.getNumeroId() != null) {
            equipo.setNumeroIdDescifrado(cryptoService.decrypt(equipo.getNumeroId()));
        }

        // Descifrar número de serie
        if (equipo.getNumeroSerie() != null) {
            equipo.setNumeroSerieDescifrado(cryptoService.decrypt(equipo.getNumeroSerie()));
        }

        // Descifrar número de kit
        if (equipo.getNumeroKit() != null) {
            equipo.setNumeroKitDescifrado(cryptoService.decrypt(equipo.getNumeroKit()));
        }

        // Descifrar notas
        if (equipo.getNotas() != null) {
            equipo.setNotasDescifrado(cryptoService.decrypt(equipo.getNotas()));
        }
    }
}

