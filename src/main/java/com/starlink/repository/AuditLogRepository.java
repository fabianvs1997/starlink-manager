package com.starlink.repository;

import com.starlink.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUsuario(String usuario);

    List<AuditLog> findByEntidadAndEntidadId(String entidad, Long entidadId);

    List<AuditLog> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT a FROM AuditLog a WHERE a.fecha >= :desde ORDER BY a.fecha DESC")
    List<AuditLog> findRecientes(LocalDateTime desde);
}
