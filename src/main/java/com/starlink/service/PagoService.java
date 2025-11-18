package com.starlink.service;

import com.starlink.dto.PagoDTO;
import com.starlink.entity.Equipo;
import com.starlink.entity.Pago;
import com.starlink.mapper.PagoMapper;
import com.starlink.repository.EquipoRepository;
import com.starlink.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoService {

    private final PagoRepository pagoRepository;
    private final EquipoRepository equipoRepository;
    private final PagoMapper pagoMapper;
    private final EquipoService equipoService;

    public List<PagoDTO> getAllPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagoDTO getPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return pagoMapper.toDTO(pago);
    }

    public boolean validatePago(Long equipoId, BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (monto.compareTo(new BigDecimal("999999.99")) > 0) {
            return false;
        }

        return equipoRepository.existsById(equipoId);
    }

    public PagoDTO createPago(Long equipoId, PagoDTO pagoDTO) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + equipoId));

        if (!validatePago(equipoId, pagoDTO.getMonto())) {
            throw new RuntimeException("Datos de pago inválidos");
        }

        Pago pago = pagoMapper.toEntity(pagoDTO);
        pago.setEquipoId(equipoId);

        Pago saved = pagoRepository.save(pago);

        actualizarTotalesEquipo(equipo);

        return pagoMapper.toDTO(saved);
    }

    public List<PagoDTO> getPagosByEquipo(Long equipoId) {
        return pagoRepository.findByEquipoId(equipoId)
                .stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalPagadoMes(Long equipoId, YearMonth mes) {
        LocalDate inicio = mes.atDay(1);
        LocalDate fin = mes.atEndOfMonth();

        return pagoRepository.findByEquipoIdAndFechaPagoBetween(equipoId, inicio, fin)
                .stream()
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<PagoDTO> getPagosByPeriodo(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByFechaPagoBetween(inicio, fin)
                .stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalPagadoMesActual() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

        return pagoRepository.findByFechaPagoBetween(inicioMes, finMes)
                .stream()
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deletePago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        Long equipoId = pago.getEquipoId();

        pagoRepository.deleteById(id);

        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        actualizarTotalesEquipo(equipo);
    }

    private void actualizarTotalesEquipo(Equipo equipo) {
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int añoActual = hoy.getYear();

        List<Pago> pagosMesActual = pagoRepository.findByEquipoIdAndFechaPagoBetween(
                equipo.getId(),
                LocalDate.of(añoActual, mesActual, 1),
                LocalDate.of(añoActual, mesActual, hoy.lengthOfMonth())
        );

        BigDecimal totalPagado = pagosMesActual.stream()
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        equipo.setTotalPagadoMesActual(totalPagado);

        equipoService.calcularEstadoPago(equipo);

        equipoRepository.save(equipo);
    }
}
