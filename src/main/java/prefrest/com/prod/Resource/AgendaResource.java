package prefrest.com.prod.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Agenda;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaResource {

    @GetMapping()
    public ResponseEntity<List<Agenda>> getCompromissosAgenda() {
        //TODO Recuperar Lista de Compromissos Agenda
        return null;
    }

    @PostMapping()
    public ResponseEntity<Agenda> salvaAgenda(@RequestBody Agenda agenda) {
        //TODO Salvar Agenda
        return null;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity salvarAgendaInfos(@PathVariable Long codigo, Agenda agenda) {
        //TODO Atualizar Infos Cab Agenda
        return null;
    }

    @PutMapping("/{codigo}/imagem")
    public ResponseEntity salvaAtualizaImagem(@PathVariable Long codigo,
                                              @RequestPart MultipartFile file) {
        //TODO Salvar Atualizar Imagem Agenda
        return null;
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAgendaId(@PathVariable Long codigo) {
        //TODO Deletar Compromisso Agenda
    }
}
