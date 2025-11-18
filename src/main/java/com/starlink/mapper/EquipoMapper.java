package com.starlink.mapper;

import com.starlink.dto.EquipoDTO;
import com.starlink.entity.Equipo;
import org.springframework.stereotype.Component;

@Component
public class EquipoMapper {

    public EquipoDTO toDTO(Equipo equipo) {
        if (equipo == null) {
            return null;
        }

        EquipoDTO dto = new EquipoDTO();
        dto.setId(equipo.getId());
        dto.setCategoria(equipo.getCategoria());
        dto.setNombre(equipo.getNombre());
        dto.setCorreo(equipo.getCorreo());
        dto.setContraseña(equipo.getContraseña());
        dto.setMontoMensual(equipo.getMontoMensual());
        dto.setVencimiento(equipo.getVencimiento());
        dto.setCuentaTarjeta(equipo.getCuentaTarjeta());
        dto.setNumeroEquipos(equipo.getNumeroEquipos());
        dto.setNumeroId(equipo.getNumeroId());
        dto.setNumeroSerie(equipo.getNumeroSerie());
        dto.setNumeroKit(equipo.getNumeroKit());
        dto.setActivo(equipo.getActivo());
        dto.setEstadoPago(equipo.getEstadoPago());
        dto.setDeudaMensual(equipo.getDeudaMensual());
        dto.setTotalPagadoMesActual(equipo.getTotalPagadoMesActual());
        dto.setNotas(equipo.getNotas());
        dto.setFechaCreacion(equipo.getFechaCreacion());
        dto.setUltimaModificacion(equipo.getUltimaModificacion());

        return dto;
    }

    public Equipo toEntity(EquipoDTO dto) {
        if (dto == null) {
            return null;
        }

        Equipo equipo = new Equipo();
        equipo.setId(dto.getId());
        equipo.setCategoria(dto.getCategoria());
        equipo.setNombre(dto.getNombre());
        equipo.setCorreo(dto.getCorreo());
        equipo.setContraseña(dto.getContraseña());
        equipo.setMontoMensual(dto.getMontoMensual());
        equipo.setVencimiento(dto.getVencimiento());
        equipo.setCuentaTarjeta(dto.getCuentaTarjeta());
        equipo.setNumeroEquipos(dto.getNumeroEquipos());
        equipo.setNumeroId(dto.getNumeroId());
        equipo.setNumeroSerie(dto.getNumeroSerie());
        equipo.setNumeroKit(dto.getNumeroKit());
        equipo.setActivo(dto.getActivo());
        equipo.setEstadoPago(dto.getEstadoPago());
        equipo.setDeudaMensual(dto.getDeudaMensual());
        equipo.setTotalPagadoMesActual(dto.getTotalPagadoMesActual());
        equipo.setNotas(dto.getNotas());

        return equipo;
    }
}
