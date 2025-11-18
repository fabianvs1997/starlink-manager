package com.starlink.repository;

import com.starlink.entity.Notificacion;
import com.starlink.entity.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByEnviadoFalse();

    List<Notificacion> findByEquipoId(Long equipoId);

    List<Notificacion> findByTipo(TipoNotificacion tipo);

    long countByEnviadoFalse();
}
