package com.starlink.service;

import com.starlink.dto.EstadisticasDTO;
import com.starlink.entity.Equipo;
import com.starlink.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticasService {

    private final EquipoRepository equipoRepository;

    public EstadisticasDTO getEstadisticasGenerales() {
        List<Equipo> todosEquipos = equipoRepository.findAll();
        List<Equipo> equiposActivos = equipoRepository.findByActivo("SI");

        long totalEquipos = todosEquipos.size();
        long equiposActivosCount = equiposActivos.size();
        long equiposCancelados = totalEquipos - equiposActivosCount;

        long equiposVencidos = equiposActivos.stream()
                .filter(e -> "VENCIDO".equals(e.getEstadoPago()))
                .count();

        long equiposPendientePago = equiposActivos.stream()
                .filter(e -> "PENDIENTE".equals(e.getEstadoPago()))
                .count();

        BigDecimal montoTotalMensual = equiposActivos.stream()
                .map(Equipo::getMontoMensual)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal montoPagadoMesActual = equiposActivos.stream()
                .map(e -> e.getTotalPagadoMesActual() != null ? e.getTotalPagadoMesActual() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal deudaTotalMesActual = equiposActivos.stream()
                .map(e -> e.getDeudaMensual() != null ? e.getDeudaMensual() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long proximoVencimiento = equiposActivos.stream()
                .filter(e -> e.getVencimiento() != null)
                .map(e -> ChronoUnit.DAYS.between(LocalDate.now(), e.getVencimiento()))
                .filter(dias -> dias >= 0)
                .min(Long::compareTo)
                .orElse(0L);

        EstadisticasDTO stats = new EstadisticasDTO();
        stats.setTotalEquipos(totalEquipos);
        stats.setEquiposActivos(equiposActivosCount);
        stats.setEquiposCancelados(equiposCancelados);
        stats.setEquiposVencidos(equiposVencidos);
        stats.setEquiposPendientePago(equiposPendientePago);
        stats.setMontoTotalMensual(montoTotalMensual);
        stats.setMontoPagadoMesActual(montoPagadoMesActual);
        stats.setDeudaTotalMesActual(deudaTotalMesActual);
        stats.setProximoVencimiento(proximoVencimiento);

        return stats;
    }

    public BigDecimal getTotalDeuda() {
        return equipoRepository.findByActivo("SI")
                .stream()
                .map(e -> e.getDeudaMensual() != null ? e.getDeudaMensual() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

