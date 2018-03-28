package prefrest.com.prod.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.enquetes.Pergunta;

@RestController
@RequestMapping("/respostas")
public class RespostaResource {

    @DeleteMapping("/{codigo}")
    public void removerPergunta() {

    }

    @PostMapping()
    public ResponseEntity<Pergunta> salvarPergunta() {
        return null;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pergunta> atualizarPergunta() {
        return null;
    }

}
