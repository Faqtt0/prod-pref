package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return usuarioService.recuperaUsuarios();
    }

    @PostMapping()
    public ResponseEntity<Usuario> salvaUsuario(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
        return usuarioService.salvarUsuario(usuario, publisher, response);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizarUsuario(@PathVariable Long codigo,
                                           @Valid @RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(codigo, usuario);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity deleteUsuario(@PathVariable Long codigo){
        return usuarioService.deleteUsuario(codigo);
    }
}
