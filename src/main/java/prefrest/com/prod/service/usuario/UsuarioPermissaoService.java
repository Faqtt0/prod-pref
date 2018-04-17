package prefrest.com.prod.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Permissao;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.repository.usuario.PermissaoRepository;
import prefrest.com.prod.repository.usuario.UsuarioPermissaoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioPermissaoService {
    @Autowired
    UsuarioPermissaoRepository usuarioPermissaoRepository;

    @Autowired
    PermissaoRepository permissaoRepository;

    public ResponseEntity<Map<String, Object>> recuperarAcessos(Long codTipoUser) {
        Map<String, Object> permissoes = new HashMap<>();
        List<Permissao> listaPermissao = permissaoRepository.findAllOrderById();
        listaPermissao.forEach(permissao -> permissao.setPermissao(permissao.getPermissao().replace("ROLE_", "").replace("_", " ")));
        permissoes.put("permissoes", listaPermissao);

        List<UsuarioPermissao> permissoesUsuarios = usuarioPermissaoRepository.findAllByCodTipoUsuarioOrderByCodPermissao(codTipoUser);
        permissoes.put("permissoesTipoUser", permissoesUsuarios);
        return ResponseEntity.ok().body(permissoes);
    }

    public ResponseEntity salvarPermissao(UsuarioPermissao usuarioPermissao) {
        UsuarioPermissao permissaoSalva = usuarioPermissaoRepository.save(usuarioPermissao);
        return permissaoSalva != null ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.badRequest().build();
    }

    public ResponseEntity salvarPermissoes(List<UsuarioPermissao> permissoes) {
        List<UsuarioPermissao> permissoesSalvas = usuarioPermissaoRepository.save(permissoes);
        if (permissoesSalvas != null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity deletarPermissoes(Long codTipoUser) {
        int registros = usuarioPermissaoRepository.deleteAllByCodTipoUsuario(codTipoUser);
        return registros > 0 ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    public ResponseEntity deletarPermissaoTipoUser(Long codPermissao, Long codTipoUser) {
        int registros = usuarioPermissaoRepository.deleteByCodPermissaoAndCodTipoUsuario(codPermissao, codTipoUser);
        return registros > 0 ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
}
