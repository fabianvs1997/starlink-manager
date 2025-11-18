package com.starlink.controller;

import com.starlink.dto.ApiResponse;
import com.starlink.service.StarlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/starlink")
@CrossOrigin(origins = "*")
public class StarlinkController {

    @Autowired
    private StarlinkService starlinkService;

    /**
     * Obtener estadísticas en tiempo real del terminal Starlink
     * GET /api/starlink/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStarlinkStats() {
        try {
            Map<String, Object> stats = starlinkService.getStarlinkStats();
            return ResponseEntity.ok(ApiResponse.success("Estadísticas de Starlink", stats));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }

    /**
     * Verificar conexión con el terminal Starlink
     * GET /api/starlink/status
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatus() {
        try {
            Map<String, Object> stats = starlinkService.getStarlinkStats();
            boolean isConnected = (boolean) stats.getOrDefault("isConnected", false);
            String message = isConnected ? "Conectado al terminal Starlink" : "Desconectado del terminal Starlink";

            return ResponseEntity.ok(ApiResponse.success(message, stats));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al verificar estado: " + e.getMessage()));
        }
    }

    /**
     * Obtener métricas simplificadas
     * GET /api/starlink/metrics
     */
    @GetMapping("/metrics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMetrics() {
        try {
            Map<String, Object> stats = starlinkService.getStarlinkStats();

            Map<String, Object> metrics = Map.of(
                    "downlinkMbps", stats.getOrDefault("downlinkMbps", 0.0),
                    "uplinkMbps", stats.getOrDefault("uplinkMbps", 0.0),
                    "latencyMs", stats.getOrDefault("latencyMs", 0.0),
                    "packetLossPercent", stats.getOrDefault("packetLossPercent", 0.0)
            );

            return ResponseEntity.ok(ApiResponse.success("Métricas de red", metrics));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener métricas: " + e.getMessage()));
        }
    }
}
