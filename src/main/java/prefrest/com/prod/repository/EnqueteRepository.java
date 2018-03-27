package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.Enquete;

import java.util.List;

public interface EnqueteRepository extends JpaRepository<Enquete, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE ORDER BY ID DESC limit 100")
    List<Enquete> findByEnquetesLimit();

    List<Enquete> findByIdOrderByIdDesc(Long id);
    List<Enquete> findByDescricaoContainingOrderByDescricao(String descricao);

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE WHERE ATIVO = TRUE ")
    List<Enquete> findByAtivoOrderByIdDesc();

}
