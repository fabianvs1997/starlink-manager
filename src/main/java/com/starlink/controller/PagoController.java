package com.starlink.controller;

import com.starlink.dto.PagoDTO;
import com.starlink.dto.ApiResponse;
import com.starlink.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/equipo/{equipoId}")
    public ResponseEntity<ApiResponse<PagoDTO>> createPago(
            @PathVariable Long equipoId,
            @RequestBody PagoDTO pagoDTO) {

        if (!pagoService.validatePago(equipoId, pagoDTO.getMonto())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Datos de pago inválidos"));
        }

        PagoDTO created = pagoService.createPago(equipoId, pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pago registrado exitosamente", created));
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<ApiResponse<List<PagoDTO>>> getPagosByEquipo(@PathVariable Long equipoId) {
        List<PagoDTO> pagos = pagoService.getPagosByEquipo(equipoId);
        return ResponseEntity.ok(ApiResponse.success("Pagos del equipo", pagos));
    }

    @GetMapping("/equipo/{equipoId}/mes/{mes}")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalPagadoMes(
            @PathVariable Long equipoId,
            @PathVariable String mes) {
        YearMonth yearMonth = YearMonth.parse(mes);
        BigDecimal total = pagoService.getTotalPagadoMes(equipoId, yearMonth);
        return ResponseEntity.ok(ApiResponse.success("Total pagado en el mes", total));
    }

    @GetMapping("/periodo")
    public ResponseEntity<ApiResponse<List<PagoDTO>>> getPagosByPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        List<PagoDTO> pagos = pagoService.getPagosByPeriodo(inicio, fin);
        return ResponseEntity.ok(ApiResponse.success("Pagos del período", pagos));
    }

    @GetMapping("/total-mes-actual")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalPagadoMesActual() {
        BigDecimal total = pagoService.getTotalPagadoMesActual();
        return ResponseEntity.ok(ApiResponse.success("Total pagado este mes", total));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.ok(ApiResponse.success("Pago eliminado exitosamente", null));
    }
}
