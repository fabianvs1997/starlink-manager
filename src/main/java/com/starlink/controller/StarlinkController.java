package com.starlink.controller;

import com.starlink.dto.StarlinkStats;
import com.starlink.service.StarlinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/starlink")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class StarlinkController {

    private final StarlinkService starlinkService;

    /**
     * GET /api/starlink/stats
     * Retorna las estadísticas actuales de Starlink
     */
    @GetMapping("/stats")
    public ResponseEntity<StarlinkStats> obtenerEstadisticas() {
        StarlinkStats stats = starlinkService.obtenerEstadoActual();
        if (stats != null) {
            return ResponseEntity.ok(stats);
        }
        return ResponseEntity.status(503).body(null);
    }

    /**
     * GET /api/starlink/health
     * Retorna el estado de salud de Starlink
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> obtenerSalud() {
        StarlinkStats stats = starlinkService.obtenerEstadoActual();

        Map<String, Object> health = new HashMap<>();
        if (stats != null && stats.getIsConnected()) {
            health.put("status", "UP");
            health.put("message", stats.getStatusMessage());
            health.put("latency", stats.getLatencyMs() + " ms");
            health.put("speed", stats.getDownlinkMbps().intValue() + " Mbps ↓");
            return ResponseEntity.ok(health);
        }

        health.put("status", "DOWN");
        health.put("message", "Starlink no accesible");
        return ResponseEntity.status(503).body(health);
    }
}
