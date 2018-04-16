package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
