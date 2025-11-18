package com.starlink.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StarlinkService {

    @Value("${starlink.local.ip:192.168.100.1}")
    private String starlinkIp;

    @Value("${starlink.local.port:9200}")
    private int starlinkPort;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Obtener estadísticas del terminal Starlink
     */
    public Map<String, Object> getStarlinkStats() {
        Map<String, Object> stats = new HashMap<>();

        try {
            String url = String.format("http://%s:%d/api/status", starlinkIp, starlinkPort);
            log.debug("Consultando Starlink en: {}", url);

            String response = restTemplate.getForObject(url, String.class);

            if (response != null) {
                JsonNode jsonNode = objectMapper.readTree(response);

                stats.put("isConnected", true);
                stats.put("downlinkMbps", jsonNode.path("downlink_throughput_bps").asDouble() / 1_000_000);
                stats.put("uplinkMbps", jsonNode.path("uplink_throughput_bps").asDouble() / 1_000_000);
                stats.put("latencyMs", jsonNode.path("pop_ping_latency_ms").asDouble());
                stats.put("packetLossPercent", jsonNode.path("pop_ping_drop_rate").asDouble() * 100);
                stats.put("obstructionPercent", jsonNode.path("obstruction_percent_time").asDouble() * 100);
                stats.put("statusMessage", "Conectado - Funcionando correctamente");

                log.info("Estadísticas obtenidas exitosamente");
            }
        } catch (Exception e) {
            log.error("Error al obtener estadísticas de Starlink: {}", e.getMessage());

            // Retornar valores por defecto cuando no hay conexión
            stats.put("isConnected", false);
            stats.put("downlinkMbps", 0.0);
            stats.put("uplinkMbps", 0.0);
            stats.put("latencyMs", 0.0);
            stats.put("packetLossPercent", 0.0);
            stats.put("obstructionPercent", 0.0);
            stats.put("statusMessage", "Desconectado - No se puede conectar al terminal Starlink");
            stats.put("errorMessage", e.getMessage());
        }

        return stats;
    }
}

