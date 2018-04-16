package prefrest.com.prod.Resource.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TipoUsuario>> recuperaTipoUsuario(){
        return service.getTipoUsuarios();
    }

    @PostMapping()
    public ResponseEntity<TipoUsuario> salvaTipoUsuario(@Valid @RequestBody TipoUsuario tipoUsuario, HttpServletResponse response){
        return  service.salvarTipoUsuario(tipoUsuario, publisher, response);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizaTipoUsuario(@PathVariable Long codigo,
                                              @Valid @RequestBody TipoUsuario tipoUsuario){
        return service.atualizar(codigo, tipoUsuario);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity deletarTiposUsuario(@PathVariable Long codigo){
        return service.deletar(codigo);
    }
}
