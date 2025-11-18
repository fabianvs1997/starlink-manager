package com.starlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "recordatorios", indexes = {
        @Index(name = "idx_fecha", columnList = "fecha_recordatorio"),
        @Index(name = "idx_completado", columnList = "completado"),
        @Index(name = "idx_equipo", columnList = "equipo_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @Column(length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_recordatorio", nullable = false)
    private LocalDateTime fechaRecordatorio;

    @Column(nullable = false)
    private Boolean completado = false;

    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(length = 50)
    private String creado_por;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}

