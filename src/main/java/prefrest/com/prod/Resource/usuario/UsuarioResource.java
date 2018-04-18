package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.Usuario;
import prefrest.com.prod.service.usuario.UsuarioService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return usuarioService.recuperaUsuarios();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity<Usuario> salvaUsuario(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
        return usuarioService.salvarUsuario(usuario, publisher, response);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_USUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarUsuario(@PathVariable Long codigo,
                                           @Valid @RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(codigo, usuario);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_USUARIO') and #oauth2.hasScope('write')")
    public ResponseEntity deleteUsuario(@PathVariable Long codigo){
        return usuarioService.deleteUsuario(codigo);
    }
}
