package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import prefrest.com.prod.model.empresas.Empresa;

public interface EmpresaRespository extends JpaRepository <Empresa, Long> {

    @Transactional
    void deleteByCodSegmento(Long codigo);

}
