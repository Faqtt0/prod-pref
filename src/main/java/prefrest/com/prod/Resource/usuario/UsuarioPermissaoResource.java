package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.service.usuario.UsuarioPermissaoService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permissaouser")
public class UsuarioPermissaoResource {

    @Autowired
    UsuarioPermissaoService usuarioPermissaoService;

    @GetMapping("/{codTipoUser}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIOPERMISSAO') and #oauth2.hasScope('read')")
    public ResponseEntity<Map<String, Object>> recuperaDireitosAcessosUsuario(@PathVariable Long codTipoUser) {
        return usuarioPermissaoService.recuperarAcessos(codTipoUser);
    }

    //TODO PERMISSAO TIPOUSUARIO LEMBRAR DE CHAMAR deletar o registro se desmarcar a permissão
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIOPERMISSAO') and #oauth2.hasScope('write')")
    public ResponseEntity salvaPermissaoTipoUser(@Valid @RequestBody UsuarioPermissao usuarioPermissao) {
        return usuarioPermissaoService.salvarPermissao(usuarioPermissao);
    }


    //TODO PERMISSAO TIPOUSUARIO LEMBRAR DE CHAMAR deletar a lista inteira pra depois inserir
    @PostMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIOPERMISSAO') and #oauth2.hasScope('write')")
    public ResponseEntity salvaListaUsuarioPermissao(@Valid @RequestBody List<UsuarioPermissao> permissoes) {
        return usuarioPermissaoService.salvarPermissoes(permissoes);
    }

    @DeleteMapping("/all/{codTipoUser}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_USUARIOPERMISSAO') and #oauth2.hasScope('write')")
    public ResponseEntity deletaTodasPermissoesTipoUsuario(@PathVariable Long codTipoUser) {
        return usuarioPermissaoService.deletarPermissoes(codTipoUser);
    }

    @DeleteMapping("/{codPermissao}/{codTipoUser}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_USUARIOPERMISSAO') and #oauth2.hasScope('write')")
    public ResponseEntity deletaPermissaoTipoUser(@PathVariable Long codPermissao,
                                                  @PathVariable Long codTipoUser) {
        return usuarioPermissaoService.deletarPermissaoTipoUser(codPermissao, codTipoUser);
    }

}
