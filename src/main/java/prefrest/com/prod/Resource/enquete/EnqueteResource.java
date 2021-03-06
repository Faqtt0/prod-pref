package prefrest.com.prod.Resource.enquete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.EnquetePersonRepository;
import prefrest.com.prod.repository.filter.EnqueteFilter;
import prefrest.com.prod.repository.filter.FiltroPadrao;
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
    public List<Enquete> retornaEnquetes(@RequestParam("id") Integer id,
                                          @RequestParam("descricao") String descricao,
                                          @RequestParam("ativo") boolean ativo) {
        return repository.filtrarEnquetes(id, descricao, ativo);
    }*/

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ENQUETE') and #oauth2.hasScope('read')")
    @GetMapping("/all")
    private ResponseEntity<List<Enquete>> recuperaEnquetesTotem(FiltroPadrao filtroPadrao){
        return service.getAllenquetes(filtroPadrao);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ENQUETE') and #oauth2.hasScope('read')")
    public List<Enquete> retornaEnquetes(EnqueteFilter filtro) {return repository.filtrarEnquetes(filtro);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ENQUETE') and #oauth2.hasScope('read')")
    public ResponseEntity<Enquete> carregarEnqueteParc (@PathVariable Long id){
        return service.carregaEnqueteParcial(id);
    }

    @GetMapping("/{id}/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_ENQUETE') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Resposta>> buscarRespostas (@PathVariable Long id, @PathVariable Long codigo){
        return service.buscaRespostasPergunta(id, codigo);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_ENQUETE') and #oauth2.hasScope('write')")
    public ResponseEntity<Enquete> salvarEnquete(@Valid @RequestBody Enquete enquete, HttpServletResponse response){
        Enquete enqueteSalva = service.salvar(enquete);
        publisher.publishEvent(new RecursoEvent(this, response, enqueteSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(enqueteSalva);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_ENQUETE') and #oauth2.hasScope('write')")
    public ResponseEntity<Enquete> atualizarEnquete(@PathVariable Long id, @Valid @RequestBody Enquete enquete) {
        return service.atualizaEnquete(id, enquete);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_ENQUETE') and #oauth2.hasScope('write')")
    public ResponseEntity remover (@PathVariable Long codigo) {
        if (service.deletarEnquete(codigo)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
