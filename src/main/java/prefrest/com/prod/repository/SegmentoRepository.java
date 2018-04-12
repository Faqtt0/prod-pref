package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.empresas.Segmento;

import java.time.LocalDateTime;
import java.util.List;

public interface SegmentoRepository extends JpaRepository<Segmento, Long> {
    List<Segmento> findAllByUltAltAfterOrderByUltAlt(LocalDateTime ultAlt);
}
