package com.starlink.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EquipoDTO {
    private Long id;
    private String categoria;
    private String nombre;
    private String correo;
    private String contraseña;
    private BigDecimal montoMensual;
    private LocalDate vencimiento;
    private String cuentaTarjeta;
    private Integer numeroEquipos;
    private String numeroId;
    private String numeroSerie;
    private String numeroKit;
    private String activo;
    private String estadoPago;
    private BigDecimal deudaMensual;
    private BigDecimal totalPagadoMesActual;
    private String notas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
}
