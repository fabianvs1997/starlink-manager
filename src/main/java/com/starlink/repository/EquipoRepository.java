package com.starlink.repository;

import com.starlink.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByCategoria(String categoria);

    List<Equipo> findByNombreContainingIgnoreCase(String nombre);

    List<Equipo> findByActivo(String activo);

    @Query("SELECT e FROM Equipo e WHERE e.vencimiento <= :fecha ORDER BY e.vencimiento ASC")
    List<Equipo> findProximosVencer(@Param("fecha") LocalDate fecha);

    @Query("SELECT e FROM Equipo e WHERE e.vencimiento < :fecha")
    List<Equipo> findVencidos(@Param("fecha") LocalDate fecha);

    @Query("SELECT COUNT(e) FROM Equipo e")
    long countTotalEquipos();

    @Query("SELECT COUNT(e) FROM Equipo e WHERE e.activo = 'SI'")
    long countEquiposActivos();



    List<Equipo> findByEstadoPago(String estadoPago);
}
