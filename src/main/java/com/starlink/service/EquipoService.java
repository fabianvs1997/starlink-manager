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
    private final CryptoService cryptoService;  // ✅ INYECTAR CRYPTO SERVICE

    /**
     * Obtener todos los equipos
     * Devuelve datos tal cual están en BD (cifrados o sin cifrar)
     * El frontend se encarga de descifrar
     */
    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener equipo por ID
     * Devuelve datos tal cual están en BD
     */
    public EquipoDTO getEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
        return equipoMapper.toDTO(equipo);
    }

    /**
     * Crear equipo
     * Recibe datos en TEXTO PLANO desde frontend
     * CIFRA antes de guardar en BD
     */
    public EquipoDTO createEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = equipoMapper.toEntity(equipoDTO);
        equipo.setActivo("SI");
        equipo.setDeudaMensual(equipo.getMontoMensual());
        equipo.setTotalPagadoMesActual(BigDecimal.ZERO);
        calcularEstadoPago(equipo);

        // ✅ CIFRAR ANTES DE GUARDAR
        equipo = cifrarEquipo(equipo);

        Equipo saved = equipoRepository.save(equipo);
        return equipoMapper.toDTO(saved);
    }

    /**
     * Actualizar equipo
     * Recibe datos en TEXTO PLANO desde frontend
     * CIFRA antes de guardar en BD
     */
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

        // ✅ CIFRAR ANTES DE GUARDAR
        equipoExistente = cifrarEquipo(equipoExistente);

        Equipo updated = equipoRepository.save(equipoExistente);
        return equipoMapper.toDTO(updated);
    }

    /**
     * Eliminar equipo
     */
    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new RuntimeException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }

    /**
     * Buscar por categoría
     */
    public List<EquipoDTO> getEquiposByCategoria(String categoria) {
        return equipoRepository.findByCategoria(categoria)
                .stream()
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Buscar equipos por texto
     * ⚠️ IMPORTANTE: La búsqueda solo funciona con datos SIN CIFRAR
     * Si el dato está cifrado en BD, la búsqueda no lo encontrará
     */
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

    /**
     * Obtener equipos próximos a vencer
     */
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

    /**
     * Obtener equipos vencidos
     */
    public List<EquipoDTO> getEquiposVencidos() {
        LocalDate hoy = LocalDate.now();

        return equipoRepository.findAll()
                .stream()
                .filter(e -> "SI".equals(e.getActivo()))
                .filter(e -> e.getVencimiento() != null && e.getVencimiento().isBefore(hoy))
                .map(equipoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Calcular estado de pago
     */
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

    /**
     * Buscar por estado de pago
     */
    public List<Equipo> buscarPorEstadoPago(String estadoPago) {
        return equipoRepository.findByEstadoPago(estadoPago);
    }

    // ========================================
    // MÉTODOS DE CIFRADO
    // ========================================

    /**
     * Cifrar datos sensibles del equipo
     * Asume que los datos llegan en TEXTO PLANO desde el frontend
     */
    private Equipo cifrarEquipo(Equipo equipo) {
        if (equipo == null) return null;

        try {
            System.out.println("🔒 Cifrando equipo ID: " + equipo.getId());

            // Cifrar nombre
            if (equipo.getNombre() != null && !equipo.getNombre().isEmpty()) {
                String original = equipo.getNombre();
                String cifrado = cryptoService.encrypt(original);
                equipo.setNombre(cifrado);
                System.out.println("✅ Nombre cifrado: " + original + " → " + cifrado.substring(0, Math.min(20, cifrado.length())) + "...");
            }

            // Cifrar correo
            if (equipo.getCorreo() != null && !equipo.getCorreo().isEmpty()) {
                equipo.setCorreo(cryptoService.encrypt(equipo.getCorreo()));
            }

            // Cifrar contraseña
            if (equipo.getContraseña() != null && !equipo.getContraseña().isEmpty()) {
                equipo.setContraseña(cryptoService.encrypt(equipo.getContraseña()));
            }

            // Cifrar cuenta/tarjeta
            if (equipo.getCuentaTarjeta() != null && !equipo.getCuentaTarjeta().isEmpty()) {
                equipo.setCuentaTarjeta(cryptoService.encrypt(equipo.getCuentaTarjeta()));
            }

            // Cifrar número ID
            if (equipo.getNumeroId() != null && !equipo.getNumeroId().isEmpty()) {
                equipo.setNumeroId(cryptoService.encrypt(equipo.getNumeroId()));
            }

            // Cifrar número de serie
            if (equipo.getNumeroSerie() != null && !equipo.getNumeroSerie().isEmpty()) {
                equipo.setNumeroSerie(cryptoService.encrypt(equipo.getNumeroSerie()));
            }

            // Cifrar número de kit
            if (equipo.getNumeroKit() != null && !equipo.getNumeroKit().isEmpty()) {
                equipo.setNumeroKit(cryptoService.encrypt(equipo.getNumeroKit()));
            }

            // Cifrar notas
            if (equipo.getNotas() != null && !equipo.getNotas().isEmpty()) {
                equipo.setNotas(cryptoService.encrypt(equipo.getNotas()));
            }

            System.out.println("✅ Equipo cifrado completamente");

        } catch (Exception e) {
            System.err.println("❌ Error al cifrar equipo: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al cifrar equipo: " + e.getMessage(), e);
        }

        return equipo;
    }
}
