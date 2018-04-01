package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.empresas.Segmento;

public interface SegmentoRepository extends JpaRepository<Segmento, Long> {
}
