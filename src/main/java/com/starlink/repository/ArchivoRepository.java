package com.starlink.repository;

import com.starlink.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    List<Archivo> findByEquipoId(Long equipoId);

    List<Archivo> findByTipoArchivo(String tipoArchivo);
}
