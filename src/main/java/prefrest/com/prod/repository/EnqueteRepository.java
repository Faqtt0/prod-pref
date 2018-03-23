package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.Enquetes;

import java.util.List;

public interface EnqueteRepository extends JpaRepository<Enquetes, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE ORDER BY ID DESC limit 100")
    List<Enquetes> findByEnquetesLimit();

    List<Enquetes> findByIdOrderByIdDesc(Long id);
    List<Enquetes> findByDescricaoContainingOrderByDescricao(String descricao);

    @Query(nativeQuery = true, value = "SELECT * FROM ENQUETE WHERE ATIVO = TRUE ")
    List<Enquetes> findByAtivoOrderByIdDesc();

}
