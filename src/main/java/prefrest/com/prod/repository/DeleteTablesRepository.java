package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import prefrest.com.prod.model.DeleteTables;

import java.time.LocalDateTime;
import java.util.List;

public interface DeleteTablesRepository extends JpaRepository<DeleteTables, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM DELETETABLES ORDER BY TABELA, IDTABELA")
    List<DeleteTables> getRegistrosOrdemByTabela();

    @Transactional
    void deleteByUltAltBefore(LocalDateTime ultAlt);
}
