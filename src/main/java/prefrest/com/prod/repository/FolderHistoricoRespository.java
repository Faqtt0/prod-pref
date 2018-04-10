package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.FolderHistorico;

import java.time.LocalDateTime;
import java.util.List;

public interface FolderHistoricoRespository extends JpaRepository<FolderHistorico, Long> {
    List<FolderHistorico> findAllByUltAltAfterOrderByUltAlt(LocalDateTime dateTime);
}
