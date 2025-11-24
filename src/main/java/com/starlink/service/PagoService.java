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

        // ===== ACTUALIZAR FECHA DE VENCIMIENTO =====
        actualizarFechaVencimiento(equipo, pagoDTO.getFechaPago(), pagoDTO.getMonto());

        // Actualizar totales y estado del equipo
        actualizarTotalesEquipo(equipo);

        return pagoMapper.toDTO(saved);
    }

    /**
     * Actualizar la fecha de vencimiento del equipo al registrar un pago
     *
     * Lógica:
     * - Si el pago cubre al menos el monto mensual, se extiende el vencimiento
     * - Si el pago es a tiempo o anticipado: extiende desde fecha de vencimiento actual
     * - Si el pago es tardío: extiende desde la fecha del pago
     * - Permite pagos de múltiples meses
     */
    private void actualizarFechaVencimiento(Equipo equipo, LocalDate fechaPago, BigDecimal montoPagado) {
        LocalDate fechaVencimientoActual = equipo.getVencimiento();
        BigDecimal montoMensual = equipo.getMontoMensual();

        // Calcular cuántos meses completos cubre el pago
        int mesesCubiertos = montoPagado.divide(montoMensual, 0, BigDecimal.ROUND_DOWN).intValue();

        if (mesesCubiertos >= 1) {
            LocalDate nuevaFechaVencimiento;

            // Determinar desde qué fecha extender
            if (fechaPago.isBefore(fechaVencimientoActual) || fechaPago.isEqual(fechaVencimientoActual)) {
                // Pago a tiempo o anticipado: extender desde vencimiento actual
                nuevaFechaVencimiento = fechaVencimientoActual.plusMonths(mesesCubiertos);
            } else {
                // Pago tardío: extender desde la fecha del pago
                nuevaFechaVencimiento = fechaPago.plusMonths(mesesCubiertos);
            }

            equipo.setVencimiento(nuevaFechaVencimiento);

            System.out.println("📅 Fecha de vencimiento actualizada:");
            System.out.println("   - Anterior: " + fechaVencimientoActual);
            System.out.println("   - Nueva: " + nuevaFechaVencimiento);
            System.out.println("   - Meses cubiertos: " + mesesCubiertos);
        } else {
            System.out.println("⚠️ Pago parcial - No se actualiza fecha de vencimiento");
            System.out.println("   - Monto pagado: $" + montoPagado);
            System.out.println("   - Monto mensual: $" + montoMensual);
        }
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

        // Al eliminar un pago, recalcular el vencimiento si es necesario
        // NOTA: Esto es opcional, depende de tu lógica de negocio
        // Si eliminas un pago, podrías querer revertir la extensión del vencimiento

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
