package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.empresas.Empresa;

public interface EmpresasRespository extends JpaRepository <Empresa, Long> {

}
