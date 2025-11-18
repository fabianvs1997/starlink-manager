package com.starlink.service;

import com.starlink.entity.Equipo;
import com.starlink.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    public Equipo obtenerPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
    }

    public Equipo crear(Equipo equipo) {
        equipo.setActivo("SI");
        equipo.setDeudaMensual(equipo.getMontoMensual());
        equipo.setTotalPagadoMesActual(BigDecimal.ZERO);
        calcularEstadoPago(equipo);
        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo equipoActualizado) {
        Equipo equipoExistente = obtenerPorId(id);

        equipoExistente.setCategoria(equipoActualizado.getCategoria());
        equipoExistente.setNombre(equipoActualizado.getNombre());
        equipoExistente.setCorreo(equipoActualizado.getCorreo());
        equipoExistente.setContraseña(equipoActualizado.getContraseña());
        equipoExistente.setMontoMensual(equipoActualizado.getMontoMensual());
        equipoExistente.setVencimiento(equipoActualizado.getVencimiento());
        equipoExistente.setCuentaTarjeta(equipoActualizado.getCuentaTarjeta());
        equipoExistente.setNumeroEquipos(equipoActualizado.getNumeroEquipos());
        equipoExistente.setNumeroId(equipoActualizado.getNumeroId());
        equipoExistente.setNumeroSerie(equipoActualizado.getNumeroSerie());
        equipoExistente.setNumeroKit(equipoActualizado.getNumeroKit());
        equipoExistente.setActivo(equipoActualizado.getActivo());
        equipoExistente.setNotas(equipoActualizado.getNotas());

        calcularEstadoPago(equipoExistente);

        return equipoRepository.save(equipoExistente);
    }

    public void eliminar(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    public void calcularEstadoPago(Equipo equipo) {
        BigDecimal totalPagado = equipo.getTotalPagadoMesActual() != null
                ? equipo.getTotalPagadoMesActual()
                : BigDecimal.ZERO;

        BigDecimal deuda = equipo.getMontoMensual().subtract(totalPagado);
        equipo.setDeudaMensual(deuda);

        LocalDate hoy = LocalDate.now();

        if (totalPagado.compareTo(equipo.getMontoMensual()) >= 0) {
            equipo.setEstadoPago("PAGADO");
        } else if (equipo.getVencimiento().isBefore(hoy)) {
            equipo.setEstadoPago("VENCIDO");
        } else {
            equipo.setEstadoPago("PENDIENTE");
        }
    }

    public List<Equipo> buscarPorCategoria(String categoria) {
        return equipoRepository.findByCategoria(categoria);
    }

    public List<Equipo> buscarPorEstadoPago(String estadoPago) {
        return equipoRepository.findByEstadoPago(estadoPago);
    }

    public List<Equipo> buscarActivos() {
        return equipoRepository.findByActivo("SI");
    }
}
