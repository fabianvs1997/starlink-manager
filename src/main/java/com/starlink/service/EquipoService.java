package com.starlink.service;

import com.starlink.dto.EquipoDTO;
import com.starlink.entity.Equipo;
import com.starlink.mapper.EquipoMapper;
import com.starlink.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;

    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EquipoDTO getEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        return equipoMapper.toDTO(equipo);  // ✅ CORREGIDO
    }

    public EquipoDTO createEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = equipoMapper.toEntity(equipoDTO);
        equipo.setActivo("SI");
        equipo.setDeudaMensual(equipo.getMontoMensual());
        equipo.setTotalPagadoMesActual(BigDecimal.ZERO);
        calcularEstadoPago(equipo);
        Equipo saved = equipoRepository.save(equipo);
        return equipoMapper.toDTO(saved);
    }

    public EquipoDTO updateEquipo(Long id, EquipoDTO equipoDTO) {
        Equipo equipoExistente = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));

        equipoExistente.setCategoria(equipoDTO.getCategoria());
        equipoExistente.setNombre(equipoDTO.getNombre());
        equipoExistente.setCorreo(equipoDTO.getCorreo());
        equipoExistente.setContraseña(equipoDTO.getContraseña());
        equipoExistente.setMontoMensual(equipoDTO.getMontoMensual());
        equipoExistente.setVencimiento(equipoDTO.getVencimiento());
        equipoExistente.setCuentaTarjeta(equipoDTO.getCuentaTarjeta());
        equipoExistente.setNumeroEquipos(equipoDTO.getNumeroEquipos());
        equipoExistente.setNumeroId(equipoDTO.getNumeroId());
        equipoExistente.setNumeroSerie(equipoDTO.getNumeroSerie());
        equipoExistente.setNumeroKit(equipoDTO.getNumeroKit());
        equipoExistente.setActivo(equipoDTO.getActivo());
        equipoExistente.setNotas(equipoDTO.getNotas());

        calcularEstadoPago(equipoExistente);

        Equipo updated = equipoRepository.save(equipoExistente);
        return equipoMapper.toDTO(updated);
    }

    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    public List<EquipoDTO> getEquiposByCategoria(String categoria) {
        return equipoRepository.findByCategoria(categoria)
                .stream()
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EquipoDTO> searchEquipos(String query) {
        return equipoRepository.findAll()
                .stream()
                .filter(e ->
                        e.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                                e.getCategoria().toLowerCase().contains(query.toLowerCase()) ||
                                e.getCorreo().toLowerCase().contains(query.toLowerCase())
                )
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EquipoDTO> getEquiposProximosVencer(Integer dias) {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(dias != null ? dias : 7);

        return equipoRepository.findAll()
                .stream()
                .filter(e -> "SI".equals(e.getActivo()))
                .filter(e -> e.getVencimiento() != null)
                .filter(e -> !e.getVencimiento().isBefore(hoy) && !e.getVencimiento().isAfter(limite))
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EquipoDTO> getEquiposVencidos() {
        LocalDate hoy = LocalDate.now();

        return equipoRepository.findAll()
                .stream()
                .filter(e -> "SI".equals(e.getActivo()))
                .filter(e -> e.getVencimiento() != null && e.getVencimiento().isBefore(hoy))
                .map(equipoMapper::toDTO)  // ✅ CORREGIDO
                .collect(Collectors.toList());
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

    public List<Equipo> buscarPorEstadoPago(String estadoPago) {
        return equipoRepository.findByEstadoPago(estadoPago);
    }
}
