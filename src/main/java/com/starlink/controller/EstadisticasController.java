package com.starlink.controller;

import com.starlink.dto.EstadisticasDTO;
import com.starlink.dto.ApiResponse;
import com.starlink.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping
    public ResponseEntity<ApiResponse<EstadisticasDTO>> getEstadisticas() {
        EstadisticasDTO estadisticas = estadisticasService.getEstadisticasGenerales();
        return ResponseEntity.ok(ApiResponse.success("Estadísticas generales", estadisticas));
    }

    @GetMapping("/deuda-total")
    public ResponseEntity<ApiResponse<BigDecimal>> getDeudaTotal() {
        BigDecimal deuda = estadisticasService.getTotalDeuda();
        return ResponseEntity.ok(ApiResponse.success("Deuda total del mes", deuda));
    }
}
