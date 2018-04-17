package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.UsuarioPermissao;
import prefrest.com.prod.service.usuario.UsuarioPermissaoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissaouser")
public class UsuarioPermissaoResource {

    @Autowired
    UsuarioPermissaoService usuarioPermissaoService;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping("/{codTipoUser}")
    public ResponseEntity<List<UsuarioPermissao>> recuperaDireitosAcessosUsuario(@PathVariable Long codTipoUser){
        return usuarioPermissaoService.recuperarAcessos(codTipoUser);
    }

    @PostMapping()
    public ResponseEntity salvaUsuarioPermissao(@Valid @RequestBody List<UsuarioPermissao> permissoes){
        return null;
    }

    @PutMapping("/{codigo}")

    @DeleteMapping("/{codigo}")
}
