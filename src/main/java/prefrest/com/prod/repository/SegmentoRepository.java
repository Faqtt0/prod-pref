package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.empresas.Segmento;

import java.time.LocalDateTime;
import java.util.List;

public interface SegmentoRepository extends JpaRepository<Segmento, Long> {
    List<Segmento> findAllByUltAltAfterOrderByUltAlt(LocalDateTime ultAlt);
    @Query(value = "SELECT * FROM segmento ORDER BY IMPORTANCIA DESC", nativeQuery = true)
    List<Segmento> findAllOrderByImportancia();
}
