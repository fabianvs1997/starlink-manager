package com.starlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos", indexes = {
        @Index(name = "idx_equipo_fecha", columnList = "equipoId, fechaPago"),
        @Index(name = "idx_metodo", columnList = "metodo"),
        @Index(name = "idx_fecha_pago", columnList = "fechaPago")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long equipoId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // ========== CAMPOS ADICIONALES ==========

    @Column(length = 50)
    private String metodo = "Efectivo"; // Efectivo, Transferencia, Tarjeta, Cheque

    @Column(length = 100)
    private String referencia; // Número de referencia bancaria

    @Column(name = "comprobante_ruta", length = 500)
    private String comprobanteRuta; // Ruta del archivo de comprobante

    @Column(name = "usuario_registro", length = 50)
    private String usuarioRegistro;

    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}
