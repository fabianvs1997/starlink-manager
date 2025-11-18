package com.starlink.repository;

import com.starlink.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByEquipoId(Long equipoId);

    List<Pago> findByFechaPagoBetween(LocalDate inicio, LocalDate fin);

    List<Pago> findByEquipoIdAndFechaPagoBetween(Long equipoId, LocalDate inicio, LocalDate fin);
}
