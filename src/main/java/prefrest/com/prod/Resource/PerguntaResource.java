package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.service.PerguntaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/perguntas")
public class PerguntaResource {

    @Autowired
    PerguntaService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPergunta() {
        //TODO Deletar pergunta tem que deletar respostas
    }

    @PostMapping()
    public ResponseEntity<Pergunta> salvarPergunta(@Valid @RequestBody Pergunta pergunta,
                                                   HttpServletResponse response) {
        return service.salvarPergunta(pergunta, response, publisher);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pergunta> atualizarPergunta(@PathVariable Long codigo,
                                                      @Valid @RequestBody Pergunta pergunta) {
        return service.atualizarPergunta(codigo, pergunta);
    }

}
