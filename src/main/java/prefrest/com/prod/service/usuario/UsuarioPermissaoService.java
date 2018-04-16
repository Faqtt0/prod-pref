package prefrest.com.prod.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.repository.usuario.UsuarioPermissaoRepository;

import java.util.List;

@Service
public class UsuarioPermissaoService {
    @Autowired
    UsuarioPermissaoRepository usuarioPermissaoRepository;

    public ResponseEntity<List<UsuarioPermissao>> recuperarAcessos(Long codTipoUser) {
        List<UsuarioPermissao> permissoesUsuarios = usuarioPermissaoRepository.findAllByCodTipoUsuarioOrderByCodPermissao(codTipoUser);
        return ResponseEntity.ok().body(permissoesUsuarios);
    }
}
