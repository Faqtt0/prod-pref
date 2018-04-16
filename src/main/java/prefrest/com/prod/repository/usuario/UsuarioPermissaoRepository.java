package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.model.UsuarioPermissaoID;

import java.util.List;

public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, UsuarioPermissaoID> {
    List<UsuarioPermissao> findAllByCodTipoUsuarioOrderByCodPermissao(Long codigoTipoUser);
}
