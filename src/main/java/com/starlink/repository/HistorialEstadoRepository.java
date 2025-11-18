package com.starlink.repository;

import com.starlink.entity.HistorialEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {

    List<HistorialEstado> findByEquipoIdOrderByFechaDesc(Long equipoId);

    List<HistorialEstado> findByUsuario(String usuario);
}
