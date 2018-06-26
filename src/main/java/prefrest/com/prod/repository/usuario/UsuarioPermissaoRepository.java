package prefrest.com.prod.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.model.UsuarioPermissaoID;

import java.util.List;

public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, UsuarioPermissaoID> {
    List<UsuarioPermissao> findAllByCodTipoUsuarioOrderByCodPermissao(Long codigoTipoUser);

    @Transactional
    @Modifying
    /*@Query(nativeQuery = true, value = "DELETE FROM USUARIOPERMISSAO WHERE CODTIPOUSUARIO = ?1")*/
    int deleteAllByCodTipoUsuario(Long codTipoUsuario);

    @Transactional
    @Modifying
    int deleteByCodPermissaoAndCodTipoUsuario(Long codPermissao, Long codTipoUser);
}
