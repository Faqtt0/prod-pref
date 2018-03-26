package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.enquetes.Enquetes;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;
import prefrest.com.prod.service.EnqueteService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/enquetes")
public class EnqueteResource {

    @Autowired
    EnquetePersonRepository repository;

    @Autowired
    EnqueteService service;

    @Autowired
    ApplicationEventPublisher publisher;

    /*@GetMapping
    public List<Enquetes> retornaEnquetes(@RequestParam("id") Integer id,
                                          @RequestParam("descricao") String descricao,
                                          @RequestParam("ativo") boolean ativo) {
        return repository.filtrarEnquetes(id, descricao, ativo);
    }*/

    @GetMapping()
    public List<Enquetes> retornaEnquetes(EnqueteFilter filtro) {
        return repository.filtrarEnquetes(filtro);
    }

    @PostMapping()
    public ResponseEntity<Enquetes> salvarEnquete(@Valid @RequestBody Enquetes enquetes, HttpServletResponse response){
        Enquetes enqueteSalva = service.salvar(enquetes);
        publisher.publishEvent(new RecursoEvent(this, response, enqueteSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(enqueteSalva);
    }
}
