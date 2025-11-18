package com.starlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos", indexes = {
        @Index(name = "idx_categoria", columnList = "categoria"),
        @Index(name = "idx_estado_pago", columnList = "estadoPago"),
        @Index(name = "idx_activo", columnList = "activo"),
        @Index(name = "idx_vencimiento", columnList = "vencimiento")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 255)
    private String contraseña;

    @Column(name = "montoMensual", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMensual;

    @Column(nullable = false)
    private LocalDate vencimiento;

    @Column(name = "cuentaTarjeta", nullable = false, length = 50)
    private String cuentaTarjeta;

    @Column(name = "numeroEquipos", nullable = false)
    private Integer numeroEquipos;

    @Column(name = "numeroId", nullable = false, length = 50)
    private String numeroId;

    @Column(name = "numeroSerie", nullable = false, length = 50)
    private String numeroSerie;

    @Column(name = "numeroKit", nullable = false, length = 50)
    private String numeroKit;

    @Column(nullable = false, length = 2)
    private String activo = "SI";

    @Column(length = 50)
    private String estadoPago;

    @Column(name = "deudaMensual", precision = 10, scale = 2)
    private BigDecimal deudaMensual = BigDecimal.ZERO;

    @Column(name = "totalPagadoMesActual", precision = 10, scale = 2)
    private BigDecimal totalPagadoMesActual = BigDecimal.ZERO;

    // ========== CAMPOS ADICIONALES ==========

    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(name = "fecha_instalacion")
    private LocalDate fechaInstalacion;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitud;

    @Column(name = "direccion_instalacion", columnDefinition = "TEXT")
    private String direccionInstalacion;

    @Column(name = "contacto_emergencia", length = 100)
    private String contactoEmergencia;

    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Column(name = "ultima_modificacion")
    private LocalDateTime ultimaModificacion;

    @Column(name = "modificado_por", length = 50)
    private String modificadoPor;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

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

