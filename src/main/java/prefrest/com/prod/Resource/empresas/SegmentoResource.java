package prefrest.com.prod.Resource.empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prefrest.com.prod.model.empresas.Empresa;
import prefrest.com.prod.model.empresas.Segmento;
import prefrest.com.prod.repository.EmpresaRespository;
import prefrest.com.prod.repository.SegmentoRepository;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.SegmentoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/segmentos")
public class SegmentoResource {

    @Autowired
    SegmentoRepository segmentoRepository;

    @Autowired
    EmpresaRespository empresaRespository;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    SegmentoService service;


    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_SEGMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Segmento>> getAllSegmentos(){
        return ResponseEntity.ok(segmentoRepository.findAllOrderByImportancia());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_SEGMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Segmento>> getAllSegmentosAllEmpresas(FiltroPadrao filtroPadrao){
        return service.getAllSegByUltAlt(filtroPadrao);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_SEGMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Empresa>> getEmpresasSegmento(){
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(empresaRespository.findAll());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_SEGMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Segmento> cadastrarSegumento(@Valid @RequestBody Segmento segmento, HttpServletResponse response){
        return service.criarSegmento(segmento, publisher, response);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_SEGMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Segmento> atulizarSegmento(@PathVariable Long codigo, @Valid @RequestBody Segmento segmento){
        return service.atualizarSegmento(codigo, segmento);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_SEGMENTO') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerSegmento(@PathVariable Long codigo){
        service.deletarSegmentoEmpresa(codigo);
    }

}
