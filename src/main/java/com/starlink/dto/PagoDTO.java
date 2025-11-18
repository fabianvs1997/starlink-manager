package com.starlink.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Long id;
    private Long equipoId;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private String descripcion;
    private String metodo;
    private String referencia;
    private String usuarioRegistro;
    private String notas;
    private LocalDateTime fechaRegistro;
}
