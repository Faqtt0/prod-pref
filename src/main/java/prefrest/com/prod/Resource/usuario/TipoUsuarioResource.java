package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.TipoUsuario;
import prefrest.com.prod.service.usuario.TipoUsuarioService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipouser")
public class TipoUsuarioResource {

    @Autowired
    TipoUsuarioService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_TIPOUSUARIO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<TipoUsuario>> recuperaTipoUsuario(){
        return service.getTipoUsuarios();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_TIPOUSUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity<TipoUsuario> salvaTipoUsuario(@Valid @RequestBody TipoUsuario tipoUsuario, HttpServletResponse response){
        return  service.salvarTipoUsuario(tipoUsuario, publisher, response);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_TIPOUSUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity atualizaTipoUsuario(@PathVariable Long codigo,
                                              @Valid @RequestBody TipoUsuario tipoUsuario){
        return service.atualizar(codigo, tipoUsuario);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_TIPOUSUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity deletarTiposUsuario(@PathVariable Long codigo){
        return service.deletar(codigo);
    }
}
