package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Imagens;

public interface ImagemRepository extends JpaRepository<Imagens, Long> {
}
