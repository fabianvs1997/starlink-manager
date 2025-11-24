package com.starlink.entity;

import com.starlink.listener.EquipoEncryptionListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(EquipoEncryptionListener.class)
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========================================
    // DATOS NO SENSIBLES (SIN CIFRAR)
    // ========================================

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(name = "montoMensual", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMensual;

    @Column(nullable = false)
    private LocalDate vencimiento;

    @Column(name = "numeroEquipos", nullable = false)
    private Integer numeroEquipos;

    @Column(nullable = false, length = 2)
    private String activo = "SI";

    @Column(length = 50)
    private String estadoPago;

    @Column(name = "deudaMensual", precision = 10, scale = 2)
    private BigDecimal deudaMensual = BigDecimal.ZERO;

    @Column(name = "totalPagadoMesActual", precision = 10, scale = 2)
    private BigDecimal totalPagadoMesActual = BigDecimal.ZERO;

    @Column(name = "fecha_instalacion")
    private LocalDate fechaInstalacion;

    @Column(name = "ultima_modificacion")
    private LocalDateTime ultimaModificacion;

    @Column(name = "modificado_por", length = 50)
    private String modificadoPor;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // ========================================
    // DATOS SENSIBLES CIFRADOS (ALMACENADOS CIFRADOS EN DB)
    // ========================================

    @Column(nullable = false, length = 1000)  // Mayor tamaño para texto cifrado
    private String nombre;  // Cifrado automáticamente

    @Column(nullable = false, length = 1000)
    private String correo;  // Cifrado automáticamente

    @Column(nullable = false, length = 1000)
    private String contraseña;  // Cifrado automáticamente

    @Column(name = "cuentaTarjeta", nullable = false, length = 1000)
    private String cuentaTarjeta;  // Cifrado automáticamente

    @Column(name = "numeroId", nullable = false, length = 1000)
    private String numeroId;  // Cifrado automáticamente

    @Column(name = "numeroSerie", nullable = false, length = 1000)
    private String numeroSerie;  // Cifrado automáticamente

    @Column(name = "numeroKit", nullable = false, length = 000)
    private String numeroKit;  // Cifrado automáticamente

    @Column(columnDefinition = "TEXT")
    private String notas;  // Cifrado automáticamente

    // ========================================
    // CAMPOS TRANSIENT (SOLO EN MEMORIA - NO SE GUARDAN EN DB)
    // Usados para manejar versiones descifradas temporalmente
    // ========================================

    @Transient
    private String nombreDescifrado;

    @Transient
    private String correoDescifrado;

    @Transient
    private String contraseñaDescifrado;

    @Transient
    private String cuentaTarjetaDescifrado;

    @Transient
    private String numeroIdDescifrado;

    @Transient
    private String numeroSerieDescifrado;

    @Transient
    private String numeroKitDescifrado;

    @Transient
    private String notasDescifrado;

    // ========================================
    // LIFECYCLE CALLBACKS
    // ========================================

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        ultimaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaModificacion = LocalDateTime.now();
    }
}

