package prefrest.com.prod.Resource.enquete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.PerguntaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/perguntas")
public class PerguntaResource {

    @Autowired
    PerguntaService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PERGUNTA') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Pergunta>> retornaAllPerguntasUltAlt(FiltroPadrao filtroPadrao) {
        return service.getAllPerguntas(filtroPadrao);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PERGUNTA') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Resposta>> retornaRepostas(@PathVariable Long codigo) {
        return service.buscarRepostas(codigo);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PERGUNTA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pergunta> salvarPergunta(@Valid @RequestBody Pergunta pergunta,
                                                   HttpServletResponse response) {
        return service.salvarPergunta(pergunta, response, publisher);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PERGUNTA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pergunta> atualizarPergunta(@PathVariable Long codigo,
                                                      @Valid @RequestBody Pergunta pergunta) {
        return service.atualizarPergunta(codigo, pergunta);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_PERGUNTA') and #oauth2.hasScope('write')")
    public ResponseEntity removerPergunta(@PathVariable Long codigo) {
        if (service.deletarPerguntas(codigo)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
