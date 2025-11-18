package com.starlink.mapper;

import com.starlink.dto.PagoDTO;
import com.starlink.entity.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(Pago pago) {
        if (pago == null) {
            return null;
        }

        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setEquipoId(pago.getEquipoId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setDescripcion(pago.getDescripcion());
        dto.setMetodo(pago.getMetodo());
        dto.setReferencia(pago.getReferencia());
        dto.setUsuarioRegistro(pago.getUsuarioRegistro());
        dto.setNotas(pago.getNotas());
        dto.setFechaRegistro(pago.getFechaRegistro());

        return dto;
    }

    public Pago toEntity(PagoDTO dto) {
        if (dto == null) {
            return null;
        }

        Pago pago = new Pago();
        pago.setId(dto.getId());
        pago.setEquipoId(dto.getEquipoId());
        pago.setMonto(dto.getMonto());
        pago.setFechaPago(dto.getFechaPago());
        pago.setDescripcion(dto.getDescripcion());
        pago.setMetodo(dto.getMetodo());
        pago.setReferencia(dto.getReferencia());
        pago.setUsuarioRegistro(dto.getUsuarioRegistro());
        pago.setNotas(dto.getNotas());

        return pago;
    }
}
