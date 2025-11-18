package com.starlink.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EstadisticasDTO {
    private Long totalEquipos;
    private Long equiposActivos;
    private Long equiposCancelados;
    private Long equiposVencidos;
    private Long equiposPendientePago;
    private BigDecimal montoTotalMensual;
    private BigDecimal montoPagadoMesActual;
    private BigDecimal deudaTotalMesActual;
    private Long proximoVencimiento;
}
