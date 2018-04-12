package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.Enquete;

import java.time.LocalDateTime;
import java.util.List;

public interface EnqueteRepository extends JpaRepository<Enquete, Long> {
    List<Enquete> findByIdOrderByIdDesc(Long id);
    List<Enquete> findByDescricaoContainingOrderByDescricao(String descricao);
    List<Enquete> findAllByUltAltAfterOrderByUltAlt(LocalDateTime ultAlt);

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE ORDER BY ID DESC limit 100")
    List<Enquete> findByEnquetesLimit();

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE WHERE ATIVO = TRUE ")
    List<Enquete> findByAtivoOrderByIdDesc();

}
