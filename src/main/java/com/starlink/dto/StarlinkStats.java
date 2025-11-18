package com.starlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarlinkStats {

    // Velocidades en Mbps
    private Double downlinkMbps;
    private Double uplinkMbps;

    // Latencia en ms
    private Integer latencyMs;

    // Porcentaje de pérdida de paquetes
    private Double packetLossPercent;

    // Porcentaje de obstrucción
    private Double obstructionPercent;

    // Estado de conexión
    private Boolean isConnected;

    // Timestamp de cuando se tomaron las métricas
    private Long timestamp;

    // Métodos helper
    public String getStatusColor() {
        if (!isConnected) return "red";
        if (latencyMs > 100) return "orange";
        if (packetLossPercent > 1.0) return "orange";
        return "green";
    }

    public String getStatusMessage() {
        if (!isConnected) return "❌ Desconectado";
        if (latencyMs > 100) return "⚠️ Latencia alta";
        if (packetLossPercent > 1.0) return "⚠️ Pérdida de paquetes";
        if (obstructionPercent > 10.0) return "⚠️ Obstrucción detectada";
        return "✅ Conectado y funcionando bien";
    }
}
