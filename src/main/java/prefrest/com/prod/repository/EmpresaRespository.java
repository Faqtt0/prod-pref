package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import prefrest.com.prod.model.empresas.Empresa;

import java.time.LocalDateTime;
import java.util.List;

public interface EmpresaRespository extends JpaRepository <Empresa, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM EMPRESA ORDER BY DESCRICAO")
    List<Empresa> findAllOrderByDescricaoDesc();
    List<Empresa> findAllByUltAltAfterOrderByUltAlt(LocalDateTime ultAlt);
    List<Empresa> findAllByCodSegmento(Long codSegumento);

    @Transactional
    void deleteByCodSegmento(Long codigo);

}
