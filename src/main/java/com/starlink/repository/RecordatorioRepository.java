package com.starlink.repository;

import com.starlink.entity.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

    List<Recordatorio> findByEquipoId(Long equipoId);

    List<Recordatorio> findByCompletadoFalse();

    @Query("SELECT r FROM Recordatorio r WHERE r.completado = false AND r.fechaRecordatorio <= :fecha")
    List<Recordatorio> findRecordatoriosPendientes(LocalDateTime fecha);
}
