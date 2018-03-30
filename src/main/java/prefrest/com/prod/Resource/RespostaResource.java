package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.RespostaRepository;
import prefrest.com.prod.service.RespostaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/respostas")
public class RespostaResource {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    RespostaService service;

    @Autowired
    ApplicationEventPublisher publisher;

    @DeleteMapping("/{codigo}")
    public ResponseEntity removerResposta(@PathVariable Long codigo) {
        if (service.removerResposta(codigo)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity<Resposta> salvarResposta(@Valid @RequestBody Resposta resposta, HttpServletResponse response) {
        return service.salvarResposta(resposta, response, publisher);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Resposta> atualizarReposta(@PathVariable Long codigo,
                                                     @Valid @RequestBody Resposta resposta) {
        return service.atualizarResposta(codigo, resposta);
    }

}
