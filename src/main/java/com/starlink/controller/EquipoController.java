package com.starlink.controller;

import com.starlink.dto.ApiResponse;
import com.starlink.dto.EquipoDTO;
import com.starlink.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    /**
     * Obtener todos los equipos
     * GET /api/equipos
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getAllEquipos() {
        List<EquipoDTO> equipos = equipoService.getAllEquipos();
        return ResponseEntity.ok(ApiResponse.success("Lista de equipos", equipos));
    }

    /**
     * Obtener un equipo por ID
     * GET /api/equipos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EquipoDTO>> getEquipo(@PathVariable Long id) {
        EquipoDTO equipo = equipoService.getEquipo(id);
        return ResponseEntity.ok(ApiResponse.success("Equipo encontrado", equipo));
    }

    /**
     * Crear un nuevo equipo
     * POST /api/equipos
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EquipoDTO>> createEquipo(@RequestBody EquipoDTO equipoDTO) {
        EquipoDTO created = equipoService.createEquipo(equipoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Equipo creado exitosamente", created));
    }

    /**
     * Actualizar un equipo existente
     * PUT /api/equipos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EquipoDTO>> updateEquipo(
            @PathVariable Long id,
            @RequestBody EquipoDTO equipoDTO) {
        EquipoDTO updated = equipoService.updateEquipo(id, equipoDTO);
        return ResponseEntity.ok(ApiResponse.success("Equipo actualizado exitosamente", updated));
    }

    /**
     * Eliminar un equipo
     * DELETE /api/equipos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEquipo(@PathVariable Long id) {
        equipoService.deleteEquipo(id);
        return ResponseEntity.ok(ApiResponse.success("Equipo eliminado exitosamente", null));
    }

    /**
     * Buscar equipos por categoría
     * GET /api/equipos/categoria/{categoria}
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposByCategoria(
            @PathVariable String categoria) {
        List<EquipoDTO> equipos = equipoService.getEquiposByCategoria(categoria);
        return ResponseEntity.ok(ApiResponse.success("Equipos de la categoría: " + categoria, equipos));
    }

    /**
     * Buscar equipos por texto (nombre, categoría, correo)
     * GET /api/equipos/search?query=texto
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> searchEquipos(
            @RequestParam String query) {
        List<EquipoDTO> equipos = equipoService.searchEquipos(query);
        return ResponseEntity.ok(ApiResponse.success("Resultados de búsqueda", equipos));
    }

    /**
     * Obtener equipos próximos a vencer
     * GET /api/equipos/proximos-vencer?dias=7
     */
    @GetMapping("/proximos-vencer")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposProximosVencer(
            @RequestParam(required = false, defaultValue = "7") Integer dias) {
        List<EquipoDTO> equipos = equipoService.getEquiposProximosVencer(dias);
        return ResponseEntity.ok(ApiResponse.success(
                "Equipos próximos a vencer en " + dias + " días", equipos));
    }

    /**
     * Obtener equipos vencidos
     * GET /api/equipos/vencidos
     */
    @GetMapping("/vencidos")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposVencidos() {
        List<EquipoDTO> equipos = equipoService.getEquiposVencidos();
        return ResponseEntity.ok(ApiResponse.success("Equipos vencidos", equipos));
    }

    /**
     * Obtener equipos activos
     * GET /api/equipos/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposActivos() {
        List<EquipoDTO> equipos = equipoService.getAllEquipos()
                .stream()
                .filter(e -> "SI".equals(e.getActivo()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Equipos activos", equipos));
    }

    /**
     * Obtener equipos cancelados
     * GET /api/equipos/cancelados
     */
    @GetMapping("/cancelados")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposCancelados() {
        List<EquipoDTO> equipos = equipoService.getAllEquipos()
                .stream()
                .filter(e -> "NO".equals(e.getActivo()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Equipos cancelados", equipos));
    }

    /**
     * Obtener equipos por estado de pago
     * GET /api/equipos/estado-pago/{estado}
     * Estados válidos: PAGADO, PENDIENTE, VENCIDO
     */
    @GetMapping("/estado-pago/{estado}")
    public ResponseEntity<ApiResponse<List<EquipoDTO>>> getEquiposByEstadoPago(
            @PathVariable String estado) {
        List<EquipoDTO> equipos = equipoService.getAllEquipos()
                .stream()
                .filter(e -> estado.equalsIgnoreCase(e.getEstadoPago()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(
                "Equipos con estado: " + estado, equipos));
    }
}


