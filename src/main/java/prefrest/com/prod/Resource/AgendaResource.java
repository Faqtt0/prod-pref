package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Agenda;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.AgendaService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaResource {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    AgendaService service;

    @GetMapping()
    public ResponseEntity<List<Agenda>> retornaAgenda(FiltroPadrao filtroPadrao){
        return service.buscaAgenda(filtroPadrao);
    }

    @PostMapping()
    public ResponseEntity<Agenda> salvaAgenda(@Valid @RequestBody Agenda agenda, HttpServletResponse response){
        return service.salvarAgenda(agenda, response, publisher) ;
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizarAgenda(@PathVariable Long codigo,
                                                    @Valid @RequestBody Agenda agenda){
        return service.atualizarAgenda(codigo, agenda);
    }

    @PutMapping("/{codigo}/imagem")
    public ResponseEntity atualizarSalvarImagemAgenda(@PathVariable Long codigo,
                                                    @RequestPart MultipartFile file){
        return service.atualizarSalvarImagem(codigo, file);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity deletarImagemAgenda(@PathVariable Long codigo){
        return service.deletarImagem(codigo);
    }
}
